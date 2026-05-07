package com.rakshakavach.ui;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.rakshakavach.R;
import com.rakshakavach.data.AppDatabase;
import com.rakshakavach.data.IncidentDao;
import com.rakshakavach.data.IncidentEntity;
import com.rakshakavach.ui.adapter.IncidentAdapter;

import java.util.List;
import java.util.concurrent.Executors;

public class IncidentLogActivity extends AppCompatActivity {

    private IncidentDao dao;
    private TextView tvTotal, tvWeek, tvPrevented, tvEmpty;
    private RecyclerView rv;
    private IncidentAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incident_log);

        dao = AppDatabase.getInstance(this).incidentDao();
        tvTotal = findViewById(R.id.tv_total_incidents);
        tvWeek = findViewById(R.id.tv_this_week);
        tvPrevented = findViewById(R.id.tv_prevented);
        tvEmpty = findViewById(R.id.tv_empty);
        rv = findViewById(R.id.rv_incidents);

        rv.setLayoutManager(new LinearLayoutManager(this));
        adapter = new IncidentAdapter();
        rv.setAdapter(adapter);

        dao.getAllIncidents().observe(this, incidents -> {
            adapter.setIncidents(incidents);
            if (incidents == null || incidents.isEmpty()) {
                rv.setVisibility(View.GONE);
                tvEmpty.setVisibility(View.VISIBLE);
            } else {
                rv.setVisibility(View.VISIBLE);
                tvEmpty.setVisibility(View.GONE);
            }
            updateStats();
        });

        findViewById(R.id.btn_add_incident).setOnClickListener(v -> showAddIncidentDialog());
        findViewById(R.id.btn_back).setOnClickListener(v -> finish());
    }

    private void updateStats() {
        Executors.newSingleThreadExecutor().execute(() -> {
            int total = dao.getTotalCount();
            long weekStart = System.currentTimeMillis() - (7L * 24 * 60 * 60 * 1000);
            int week = dao.getCountSince(weekStart);
            int prevented = dao.getPreventedCount();
            runOnUiThread(() -> {
                tvTotal.setText(String.valueOf(total));
                tvWeek.setText(String.valueOf(week));
                tvPrevented.setText(String.valueOf(prevented));
            });
        });
    }

    private void showAddIncidentDialog() {
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_incident, null);

        TextInputEditText etTitle = dialogView.findViewById(R.id.et_incident_title);
        TextInputEditText etDesc = dialogView.findViewById(R.id.et_incident_desc);
        TextInputEditText etLocation = dialogView.findViewById(R.id.et_incident_location);
        RadioGroup rgSeverity = dialogView.findViewById(R.id.rg_severity);

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setView(dialogView)
                .create();
        if (dialog.getWindow() != null) dialog.getWindow().setBackgroundDrawableResource(R.drawable.bg_dialog);

        dialogView.findViewById(R.id.btn_cancel).setOnClickListener(v -> dialog.dismiss());
        dialogView.findViewById(R.id.btn_submit_incident).setOnClickListener(v -> {
            String title = etTitle.getText() != null ? etTitle.getText().toString().trim() : "";
            String desc = etDesc.getText() != null ? etDesc.getText().toString().trim() : "";
            String location = etLocation.getText() != null ? etLocation.getText().toString().trim() : "";

            if (title.isEmpty()) {
                etTitle.setError("Title required");
                return;
            }

            int selectedId = rgSeverity.getCheckedRadioButtonId();
            String severity = "HIGH";
            if (selectedId == R.id.rb_low) severity = "LOW";
            else if (selectedId == R.id.rb_medium) severity = "MEDIUM";

            final String finalSeverity = severity;
            IncidentEntity incident = new IncidentEntity(
                    title, desc, location, finalSeverity,
                    System.currentTimeMillis(), false
            );

            Executors.newSingleThreadExecutor().execute(() -> {
                dao.insert(incident);
                runOnUiThread(() -> {
                    Toast.makeText(this, "Near miss reported! Thank you for keeping everyone safe.", Toast.LENGTH_LONG).show();
                    dialog.dismiss();
                });
            });
        });

        dialog.show();
    }
}

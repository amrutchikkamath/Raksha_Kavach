package com.rakshakavach.ui;

import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.rakshakavach.R;
import com.rakshakavach.data.TaskRepository;
import com.rakshakavach.model.SafetyGear;
import com.rakshakavach.model.WorkTask;
import com.rakshakavach.ui.adapter.GearCheckAdapter;
import com.rakshakavach.utils.NotificationHelper;
import com.rakshakavach.utils.PreferenceManager;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class SafetyChecklistActivity extends AppCompatActivity {

    private PreferenceManager prefs;
    private GearCheckAdapter adapter;
    private TextView tvProgress, tvAvatarEmoji, tvGearWorn, tvAvatarStatus;
    private ProgressBar progressBar;
    private String taskId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_safety_checklist);

        prefs = new PreferenceManager(this);
        tvProgress   = findViewById(R.id.tv_progress);
        tvAvatarEmoji = findViewById(R.id.tv_avatar_emoji);
        tvGearWorn   = findViewById(R.id.tv_gear_worn);
        tvAvatarStatus = findViewById(R.id.tv_avatar_status);
        progressBar  = findViewById(R.id.progress_checklist);

        TextView tvTaskName = findViewById(R.id.tv_task_name);
        taskId = prefs.getSelectedTask();

        if (taskId == null) {
            Toast.makeText(this, "Please select a task first!", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        WorkTask task = TaskRepository.getTaskById(taskId);
        if (task == null) { finish(); return; }

        tvTaskName.setText("Task: " + task.getEmoji() + " " + task.getName());

        List<SafetyGear> gearList = task.getRequiredGear();

        // ── FIX: Restore persisted gear state so RiskMeter stays in sync ──────
        for (SafetyGear g : gearList) {
            g.setChecked(prefs.isGearChecked(taskId, g.getId()));
        }

        RecyclerView rv = findViewById(R.id.rv_checklist);
        rv.setLayoutManager(new LinearLayoutManager(this));

        // Pass taskId + prefs so adapter persists every toggle immediately
        adapter = new GearCheckAdapter(gearList, taskId, prefs, this::onGearChecked);
        rv.setAdapter(adapter);
        updateProgress(gearList);
        updateAvatar(gearList);

        MaterialButton btnComplete = findViewById(R.id.btn_complete_checklist);
        btnComplete.setOnClickListener(v -> completeChecklist(gearList));

        findViewById(R.id.btn_back).setOnClickListener(v -> finish());
    }

    private void onGearChecked(List<SafetyGear> gearList) {
        updateProgress(gearList);
        updateAvatar(gearList);
    }

    private void updateProgress(List<SafetyGear> gearList) {
        int checked = 0;
        for (SafetyGear g : gearList) if (g.isChecked()) checked++;
        tvProgress.setText(checked + " / " + gearList.size());
        int pct = gearList.isEmpty() ? 0 : (checked * 100 / gearList.size());
        progressBar.setProgress(pct);
    }

    private void updateAvatar(List<SafetyGear> gearList) {
        int checked = 0;
        StringBuilder wornGear = new StringBuilder();
        for (SafetyGear g : gearList) {
            if (g.isChecked()) { checked++; wornGear.append(g.getEmoji()).append(" "); }
        }
        int total = gearList.size();
        if (checked == 0) {
            tvAvatarEmoji.setText("👷");
            tvAvatarStatus.setText("No gear on - UNSAFE!");
            tvGearWorn.setText("Wearing: Nothing ❌");
        } else if (checked < total) {
            tvAvatarEmoji.setText("🧑‍🔧");
            tvAvatarStatus.setText("Partially geared up");
            tvGearWorn.setText("Wearing: " + wornGear.toString().trim());
        } else {
            tvAvatarEmoji.setText("🦺");
            tvAvatarStatus.setText("Fully protected! ✅");
            tvGearWorn.setText("Wearing: All gear " + wornGear.toString().trim());
        }
    }

    private void completeChecklist(List<SafetyGear> gearList) {
        int checked = 0;
        for (SafetyGear g : gearList) if (g.isChecked()) checked++;

        if (checked < gearList.size()) {
            int missing = gearList.size() - checked;
            Toast.makeText(this,
                    "⚠️ Warning: " + missing + " gear item(s) not checked!\nProceeding at YOUR OWN RISK.",
                    Toast.LENGTH_LONG).show();
        }

        String today = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        if (!today.equals(prefs.getLastCheckDate())) {
            prefs.setLastCheckDate(today);
            prefs.setStreakDays(prefs.getStreakDays() + 1);
            int points = (checked == gearList.size()) ? 20 : 10;
            prefs.addToSafetyScore(points);
            prefs.incrementChecklistCount();
            NotificationHelper.cancelPeriodicReminder(this);
            Toast.makeText(this, "✅ Checklist complete! +" + points + " safety points", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "✅ Checklist already completed today!", Toast.LENGTH_SHORT).show();
        }
        finish();
    }
}

package com.rakshakavach.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rakshakavach.R;
import com.rakshakavach.data.TaskRepository;
import com.rakshakavach.model.SafetyGear;
import com.rakshakavach.model.WorkTask;
import com.rakshakavach.ui.adapter.GearStatusAdapter;
import com.rakshakavach.ui.adapter.InjuryAdapter;
import com.rakshakavach.utils.PreferenceManager;

import java.util.List;

public class RiskMeterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_risk_meter);

        PreferenceManager prefs = new PreferenceManager(this);
        String taskId = prefs.getSelectedTask();

        if (taskId == null) {
            Toast.makeText(this, "Please select a task first!", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        WorkTask task = TaskRepository.getTaskById(taskId);
        if (task == null) { finish(); return; }

        // ── FIX: Restore persisted gear-check state before rendering ──────────
        List<SafetyGear> gearList = task.getRequiredGear();
        for (SafetyGear g : gearList) {
            g.setChecked(prefs.isGearChecked(taskId, g.getId()));
        }

        int checkedCount = 0;
        for (SafetyGear g : gearList) if (g.isChecked()) checkedCount++;

        // Effective risk drops as more gear is worn
        int baseRisk     = task.getRiskScore();
        int totalGear    = gearList.size();
        int adjustedRisk = totalGear == 0 ? baseRisk
                : baseRisk - (int)((checkedCount / (float) totalGear) * baseRisk * 0.7f);
        adjustedRisk = Math.max(5, adjustedRisk); // always show some residual risk

        TextView tvTaskHeader = findViewById(R.id.tv_task_header);
        tvTaskHeader.setText("Task: " + task.getEmoji() + " " + task.getName()
                + "  |  Gear: " + checkedCount + "/" + totalGear + " ✅");

        updateRiskDisplay(adjustedRisk, baseRisk, checkedCount, totalGear);
        setupInjuryList(gearList);
        setupGearStatusList(gearList);

        findViewById(R.id.btn_back).setOnClickListener(v -> finish());
    }

    private void updateRiskDisplay(int riskScore, int baseRisk, int checkedCount, int totalGear) {
        TextView tvRiskLevel  = findViewById(R.id.tv_risk_level);
        TextView tvRiskEmoji  = findViewById(R.id.tv_risk_emoji);
        ProgressBar progressRisk = findViewById(R.id.progress_risk);
        LinearLayout llRiskCard  = findViewById(R.id.ll_risk_card);

        // Show both current and base risk
        TextView tvSubtitle = new TextView(this);
        progressRisk.setProgress(riskScore);

        String level;
        String emoji;
        int color;
        int bgColor;

        if (riskScore >= 80) {
            level = "CRITICAL RISK";  emoji = "🚨";
            color = Color.parseColor("#B71C1C"); bgColor = Color.parseColor("#3D0000");
        } else if (riskScore >= 55) {
            level = "HIGH RISK";      emoji = "⚠️";
            color = Color.parseColor("#F44336"); bgColor = Color.parseColor("#3D1010");
        } else if (riskScore >= 30) {
            level = "MEDIUM RISK";    emoji = "🟡";
            color = Color.parseColor("#FF9800"); bgColor = Color.parseColor("#3D2A00");
        } else {
            level = "LOW RISK";       emoji = "✅";
            color = Color.parseColor("#4CAF50"); bgColor = Color.parseColor("#0A3D0A");
        }

        // If all gear checked → celebrate
        if (checkedCount == totalGear && totalGear > 0) {
            level = "PROTECTED ✅";   emoji = "🛡️";
            color = Color.parseColor("#4CAF50"); bgColor = Color.parseColor("#0A3D0A");
        }

        tvRiskLevel.setText(level);
        tvRiskLevel.setTextColor(color);
        tvRiskEmoji.setText(emoji);
        llRiskCard.setBackgroundColor(bgColor);

        progressRisk.getProgressDrawable().setColorFilter(color,
                android.graphics.PorterDuff.Mode.SRC_IN);
    }

    private void setupInjuryList(List<SafetyGear> gearList) {
        RecyclerView rv = findViewById(R.id.rv_injuries);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(new InjuryAdapter(gearList));
    }

    private void setupGearStatusList(List<SafetyGear> gearList) {
        RecyclerView rv = findViewById(R.id.rv_gear_status);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(new GearStatusAdapter(gearList));
    }
}

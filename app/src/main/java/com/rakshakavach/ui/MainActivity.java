package com.rakshakavach.ui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.rakshakavach.R;
import com.rakshakavach.data.TaskRepository;
import com.rakshakavach.model.WorkTask;
import com.rakshakavach.utils.NotificationHelper;
import com.rakshakavach.utils.PreferenceManager;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private PreferenceManager prefs;
    private TextView tvDate, tvSafetyScore, tvStreak, tvCurrentTask, tvWelcome;

    private final ActivityResultLauncher<String> notifPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), granted -> {
                if (granted) {
                    prefs.setNotificationTime(7, 0);
                    NotificationHelper.scheduleDailyReminder(this, 7, 0);
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        prefs = new PreferenceManager(this);

        // Guard: not logged in → go to Login
        if (!prefs.isLoggedIn()) {
            goToLogin();
            return;
        }

        tvDate        = findViewById(R.id.tv_date);
        tvSafetyScore = findViewById(R.id.tv_safety_score);
        tvStreak      = findViewById(R.id.tv_streak);
        tvCurrentTask = findViewById(R.id.tv_current_task);
        tvWelcome     = findViewById(R.id.tv_welcome);

        // Greet user
        String name = prefs.getLoggedInName();
        if (!name.isEmpty()) {
            tvWelcome.setText("WELCOME, " + name.split(" ")[0].toUpperCase());
            Toast.makeText(this, "Welcome, " + name.split(" ")[0] + "! Stay safe today 🦺",
                    Toast.LENGTH_SHORT).show();
        }

        setupDate();
        setupClickListeners();
        requestNotificationPermission();
        setupDailyReminder();
        checkAndStartPeriodicReminders();
    }

    private void checkAndStartPeriodicReminders() {
        String today = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        if (!today.equals(prefs.getLastCheckDate())) {
            NotificationHelper.schedulePeriodicReminder(this);
        } else {
            NotificationHelper.cancelPeriodicReminder(this);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (prefs.isLoggedIn()) {
            updateUI();
            checkAndStartPeriodicReminders();
        }
    }

    private void setupDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd MMM yyyy", Locale.getDefault());
        tvDate.setText(sdf.format(new Date()));
    }

    private void updateUI() {
        tvSafetyScore.setText(String.valueOf(prefs.getSafetyScore()));
        tvStreak.setText(prefs.getStreakDays() + " days streak 🔥");

        String taskId = prefs.getSelectedTask();
        if (taskId != null) {
            WorkTask task = TaskRepository.getTaskById(taskId);
            if (task != null) {
                tvCurrentTask.setText(task.getEmoji() + " " + task.getName());
                return;
            }
        }
        tvCurrentTask.setText("No task selected");
    }

    private void setupClickListeners() {
        findViewById(R.id.card_task_selector).setOnClickListener(v ->
                startActivity(new Intent(this, TaskSelectorActivity.class)));
        findViewById(R.id.card_checklist).setOnClickListener(v ->
                startActivity(new Intent(this, SafetyChecklistActivity.class)));
        findViewById(R.id.card_risk).setOnClickListener(v ->
                startActivity(new Intent(this, RiskMeterActivity.class)));
        findViewById(R.id.card_incident).setOnClickListener(v ->
                startActivity(new Intent(this, IncidentLogActivity.class)));
        findViewById(R.id.card_quiz).setOnClickListener(v ->
                startActivity(new Intent(this, DailyQuizActivity.class)));
        findViewById(R.id.card_score_detail).setOnClickListener(v ->
                startActivity(new Intent(this, SafetyScoreActivity.class)));
        findViewById(R.id.card_score).setOnClickListener(v ->
                startActivity(new Intent(this, SafetyScoreActivity.class)));
        findViewById(R.id.btn_change_task).setOnClickListener(v ->
                startActivity(new Intent(this, TaskSelectorActivity.class)));

        // Logout button
        findViewById(R.id.btn_logout).setOnClickListener(v -> showLogoutDialog());

        // Notification icon click (test notification)
        findViewById(R.id.iv_notification).setOnClickListener(v ->
                NotificationHelper.showSafetyReminder(this));

        findViewById(R.id.iv_notification).setOnLongClickListener(v -> {
            showLogoutDialog();
            return true;
        });
    }

    private void showLogoutDialog() {
        new com.google.android.material.dialog.MaterialAlertDialogBuilder(this)
                .setTitle("Logout")
                .setMessage("Hello " + prefs.getLoggedInName() + ", would you like to sign out?")
                .setPositiveButton("Sign Out", (d, w) -> logout())
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void logout() {
        NotificationHelper.cancelPeriodicReminder(this);
        prefs.logout();
        goToLogin();
    }

    private void goToLogin() {
        startActivity(new Intent(this, LoginActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
        finish();
    }

    private void requestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
                    != PackageManager.PERMISSION_GRANTED) {
                notifPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS);
            }
        }
    }

    private void setupDailyReminder() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
                        == PackageManager.PERMISSION_GRANTED) {
            NotificationHelper.scheduleDailyReminder(this,
                    prefs.getNotificationHour(), prefs.getNotificationMinute());
        }
    }
}

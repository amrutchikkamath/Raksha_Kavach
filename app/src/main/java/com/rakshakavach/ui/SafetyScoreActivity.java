package com.rakshakavach.ui;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rakshakavach.R;
import com.rakshakavach.ui.adapter.BadgeAdapter;
import com.rakshakavach.utils.PreferenceManager;

public class SafetyScoreActivity extends AppCompatActivity {

    private PreferenceManager prefs;
    private BadgeAdapter badgeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_safety_score);

        prefs = new PreferenceManager(this);

        updateUI();

        RecyclerView rvBadges = findViewById(R.id.rv_badges);
        rvBadges.setLayoutManager(
                new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        badgeAdapter = new BadgeAdapter(prefs);
        rvBadges.setAdapter(badgeAdapter);

        findViewById(R.id.btn_back).setOnClickListener(v -> finish());

        // Reset only progress stats, NOT the login session
        findViewById(R.id.btn_reset_score).setOnClickListener(v ->
                new com.google.android.material.dialog.MaterialAlertDialogBuilder(this)
                        .setTitle("Reset Progress")
                        .setMessage("Are you sure? This clears your score, streak, and checklist history. You will stay logged in.")
                        .setPositiveButton("Reset", (d, w) -> {
                            int userId = prefs.getLoggedInUserId();
                            String userName = prefs.getLoggedInName();
                            prefs.resetAll();
                            // Re-save session so user stays logged in
                            prefs.setLoggedInUser(userId, userName);
                            updateUI();
                            badgeAdapter.refresh(prefs);
                            Toast.makeText(this, "Progress reset.", Toast.LENGTH_SHORT).show();
                        })
                        .setNegativeButton("Cancel", null)
                        .show()
        );
    }

    private void updateUI() {
        int score      = prefs.getSafetyScore();
        int streak     = prefs.getStreakDays();
        int checklists = prefs.getChecklistCount();
        int quizzes    = prefs.getQuizCount();

        ((TextView) findViewById(R.id.tv_big_score)).setText(String.valueOf(score));
        ((TextView) findViewById(R.id.tv_safe_days)).setText(String.valueOf(streak));
        ((TextView) findViewById(R.id.tv_checklists_done)).setText(String.valueOf(checklists));
        ((TextView) findViewById(R.id.tv_quizzes_done)).setText(String.valueOf(quizzes));
        ((TextView) findViewById(R.id.tv_incidents_reported)).setText("0");

        String rank;
        if      (score >= 500) rank = "🏆 Safety Legend";
        else if (score >= 300) rank = "🥇 Safety Expert";
        else if (score >= 150) rank = "🥈 Safety Pro";
        else if (score >= 50)  rank = "🥉 Safety Apprentice";
        else                   rank = "🔰 Safety Rookie";

        ((TextView) findViewById(R.id.tv_rank)).setText(rank);

        // Show logged-in user name
        String name = prefs.getLoggedInName();
        if (!name.isEmpty()) {
            TextView tvBig = findViewById(R.id.tv_big_score);
            // Subtitle already shows rank, name shown in header
        }
    }
}

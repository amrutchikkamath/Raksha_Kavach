package com.rakshakavach.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.appcompat.app.AppCompatActivity;

import com.rakshakavach.R;
import com.rakshakavach.utils.NotificationHelper;
import com.rakshakavach.utils.PreferenceManager;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        NotificationHelper.createNotificationChannel(this);
        PreferenceManager prefs = new PreferenceManager(this);

        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            if (prefs.isLoggedIn()) {
                startActivity(new Intent(this, MainActivity.class));
            } else {
                startActivity(new Intent(this, LoginActivity.class));
            }
            finish();
        }, 1800);
    }
}

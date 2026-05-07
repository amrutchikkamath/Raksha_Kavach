package com.rakshakavach.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.rakshakavach.utils.NotificationHelper;
import com.rakshakavach.utils.PreferenceManager;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        PreferenceManager prefs = new PreferenceManager(context);
        String today = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        boolean isChecklistDone = today.equals(prefs.getLastCheckDate());

        if ("ACTION_PERIODIC_CHECK".equals(intent.getAction())) {
            if (!isChecklistDone && prefs.isLoggedIn()) {
                NotificationHelper.showSafetyReminder(context);
                // Schedule next 30s check
                NotificationHelper.schedulePeriodicReminder(context);
            }
        } else {
            // Daily morning reminder
            if (!isChecklistDone && prefs.isLoggedIn()) {
                NotificationHelper.showSafetyReminder(context);
                // Also start periodic checks if not done
                NotificationHelper.schedulePeriodicReminder(context);
            }
            // Reschedule daily reminder for next day
            NotificationHelper.scheduleDailyReminder(context, prefs.getNotificationHour(), prefs.getNotificationMinute());
        }
    }
}

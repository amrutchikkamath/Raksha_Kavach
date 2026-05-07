package com.rakshakavach.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.rakshakavach.utils.NotificationHelper;
import com.rakshakavach.utils.PreferenceManager;

public class BootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
            PreferenceManager prefs = new PreferenceManager(context);
            NotificationHelper.scheduleDailyReminder(context, prefs.getNotificationHour(), prefs.getNotificationMinute());
        }
    }
}

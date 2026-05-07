package com.rakshakavach.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceManager {
    private static final String PREF_NAME = "raksha_kavach_prefs";
    private static final String KEY_SELECTED_TASK = "selected_task";
    private static final String KEY_SAFETY_SCORE = "safety_score";
    private static final String KEY_STREAK_DAYS = "streak_days";
    private static final String KEY_LAST_CHECK_DATE = "last_check_date";
    private static final String KEY_CHECKLIST_COUNT = "checklist_count";
    private static final String KEY_QUIZ_COUNT = "quiz_count";
    private static final String KEY_LAST_QUIZ_DATE = "last_quiz_date";
    private static final String KEY_NOTIFICATION_HOUR = "notification_hour";
    private static final String KEY_NOTIFICATION_MIN = "notification_min";

    private final SharedPreferences prefs;

    public PreferenceManager(Context context) {
        prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public void setSelectedTask(String taskId) {
        prefs.edit().putString(KEY_SELECTED_TASK, taskId).apply();
    }

    public String getSelectedTask() {
        return prefs.getString(KEY_SELECTED_TASK, null);
    }

    public void setSafetyScore(int score) {
        prefs.edit().putInt(KEY_SAFETY_SCORE, score).apply();
    }

    public int getSafetyScore() {
        return prefs.getInt(KEY_SAFETY_SCORE, 0);
    }

    public void setStreakDays(int days) {
        prefs.edit().putInt(KEY_STREAK_DAYS, days).apply();
    }

    public int getStreakDays() {
        return prefs.getInt(KEY_STREAK_DAYS, 0);
    }

    public void setLastCheckDate(String date) {
        prefs.edit().putString(KEY_LAST_CHECK_DATE, date).apply();
    }

    public String getLastCheckDate() {
        return prefs.getString(KEY_LAST_CHECK_DATE, "");
    }

    public void incrementChecklistCount() {
        int count = prefs.getInt(KEY_CHECKLIST_COUNT, 0);
        prefs.edit().putInt(KEY_CHECKLIST_COUNT, count + 1).apply();
    }

    public int getChecklistCount() {
        return prefs.getInt(KEY_CHECKLIST_COUNT, 0);
    }

    public void incrementQuizCount() {
        int count = prefs.getInt(KEY_QUIZ_COUNT, 0);
        prefs.edit().putInt(KEY_QUIZ_COUNT, count + 1).apply();
    }

    public int getQuizCount() {
        return prefs.getInt(KEY_QUIZ_COUNT, 0);
    }

    public void setLastQuizDate(String date) {
        prefs.edit().putString(KEY_LAST_QUIZ_DATE, date).apply();
    }

    public String getLastQuizDate() {
        return prefs.getString(KEY_LAST_QUIZ_DATE, "");
    }

    public void addToSafetyScore(int points) {
        int current = getSafetyScore();
        setSafetyScore(current + points);
    }

    public int getNotificationHour() {
        return prefs.getInt(KEY_NOTIFICATION_HOUR, 7);
    }

    public int getNotificationMinute() {
        return prefs.getInt(KEY_NOTIFICATION_MIN, 0);
    }

    public void setNotificationTime(int hour, int minute) {
        prefs.edit()
                .putInt(KEY_NOTIFICATION_HOUR, hour)
                .putInt(KEY_NOTIFICATION_MIN, minute)
                .apply();
    }

    // ── Gear check state persistence ──────────────────────────────────────────
    // Key pattern: "gear_<taskId>_<gearId>"
    public void setGearChecked(String taskId, String gearId, boolean checked) {
        prefs.edit().putBoolean("gear_" + taskId + "_" + gearId, checked).apply();
    }

    public boolean isGearChecked(String taskId, String gearId) {
        return prefs.getBoolean("gear_" + taskId + "_" + gearId, false);
    }

    public void clearGearState(String taskId) {
        // Remove all gear keys for a task by rebuilding prefs without them
        SharedPreferences.Editor editor = prefs.edit();
        for (String key : prefs.getAll().keySet()) {
            if (key.startsWith("gear_" + taskId + "_")) {
                editor.remove(key);
            }
        }
        editor.apply();
    }

    public void clearAllGearState() {
        SharedPreferences.Editor editor = prefs.edit();
        for (String key : prefs.getAll().keySet()) {
            if (key.startsWith("gear_")) {
                editor.remove(key);
            }
        }
        editor.apply();
    }

    // ── Logged-in user session ────────────────────────────────────────────────
    private static final String KEY_LOGGED_IN_USER_ID = "logged_in_user_id";
    private static final String KEY_LOGGED_IN_NAME    = "logged_in_name";

    public void setLoggedInUser(int userId, String name) {
        prefs.edit()
                .putInt(KEY_LOGGED_IN_USER_ID, userId)
                .putString(KEY_LOGGED_IN_NAME, name)
                .apply();
    }

    public int getLoggedInUserId() {
        return prefs.getInt(KEY_LOGGED_IN_USER_ID, -1);
    }

    public String getLoggedInName() {
        return prefs.getString(KEY_LOGGED_IN_NAME, "");
    }

    public boolean isLoggedIn() {
        return getLoggedInUserId() != -1;
    }

    public void logout() {
        prefs.edit()
                .remove(KEY_LOGGED_IN_USER_ID)
                .remove(KEY_LOGGED_IN_NAME)
                .apply();
    }

    public void resetAll() {
        prefs.edit().clear().apply();
    }
}

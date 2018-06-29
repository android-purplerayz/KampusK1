package com.example.android2.kampuskannekt;

import android.content.Context;

public class SharedPreferences {

    //public static final String BASE_URL = "http://172.16.172.151/kampus_konnekt_web/index.php/api/api/";
    public static final String BASE_URL = "http://srigopalgoswami.com/saheb_kk/kampus_konnekt_web/index.php/api/api/";
    public static final String PREFS_NAME = "AOP_PREFS";
    public static final String PREFS_LOGIN_STATUS = "login_status";
    public static final String PREFS_USER_ID = "user_id";
    public static final String PREFS_SHOW_PROFILE = "show_profile";
    public static final String PREFS_USERNAME = "user_name";

    public SharedPreferences() {
        super();
    }

    public void save(Context context, String PREFS_KEY, String text) {
        android.content.SharedPreferences settings;
        android.content.SharedPreferences.Editor editor;

        settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        editor = settings.edit();

        editor.putString(PREFS_KEY, text);

        editor.commit();
    }

    public String getValue(Context context, String PREFS_KEY) {
        android.content.SharedPreferences settings;
        String text;

        settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        text = settings.getString(PREFS_KEY, null);
        return text;
    }

    public void clearSharedPreference(Context context) {
        android.content.SharedPreferences settings;
        android.content.SharedPreferences.Editor editor;

        settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        editor = settings.edit();

        editor.clear();
        editor.commit();
    }
}

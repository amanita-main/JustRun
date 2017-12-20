package com.hgstf.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Ilya on 15.12.2017.
 */

public class SavePrefs {

    private static SharedPreferences prefs;
    private static final String SETTINGS_KEY = "DATA";
    public static void init(Context ctx) {
        prefs = ctx.getSharedPreferences(SETTINGS_KEY, Context.MODE_PRIVATE);
    }
    public static void save(String key, String data) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, data);
        editor.commit();
    }

    public static String read(String key) {
        return prefs.getString(key, null);
    }

    public static boolean containsSave(String key) {
        return prefs.contains(key);
    }
}

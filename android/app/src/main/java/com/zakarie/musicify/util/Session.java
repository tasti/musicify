package com.zakarie.musicify.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Session {

    public static final String PREF_USERNAME = "preference_username";
    public static final String PREF_USERNAME_DEFAULT = null;

    public static String getCurrentUser(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString(PREF_USERNAME, PREF_USERNAME_DEFAULT);
    }

    public static boolean isSignedIn(Context context) {
        return getCurrentUser(context) != null;
    }

    public static void signIn(String username, Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        sharedPreferences.edit().putString(PREF_USERNAME, username).commit();
    }

    public static void signOut(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        sharedPreferences.edit().putString(PREF_USERNAME, null).commit();
    }

}

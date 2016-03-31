package com.bytesahft.chitchat;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Helpers {

    private static SharedPreferences getPreferenceManager() {
        return PreferenceManager.getDefaultSharedPreferences(AppGlobals.getContext());
    }

    // save boolean value for login status of user , takes boolean value as parameter
    public static void userLogin(boolean value) {
        SharedPreferences sharedPreferences = getPreferenceManager();
        sharedPreferences.edit().putBoolean(AppGlobals.USER_LOGIN_STATUS, value).apply();
    }

    // get user login status and manipulate app functions by its returned boolean value
    public static boolean isUserLoggedIn() {
        SharedPreferences sharedPreferences = getPreferenceManager();
        return sharedPreferences.getBoolean(AppGlobals.USER_LOGIN_STATUS, false);
    }

    public static void saveStringToSP(String key, String value) {
        SharedPreferences sharedPreferences = getPreferenceManager();
        sharedPreferences.edit().putString(key, value).apply();
    }

    // get user login status and manipulate app functions by its returned boolean value
    public static String getStringValueFromSP(String key) {
        SharedPreferences sharedPreferences = getPreferenceManager();
        return sharedPreferences.getString(key, "");
    }
}

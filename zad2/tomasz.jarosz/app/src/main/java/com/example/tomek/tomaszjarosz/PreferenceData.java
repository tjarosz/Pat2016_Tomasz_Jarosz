package com.example.tomek.tomaszjarosz;


import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

class PreferenceData
{
    private static final String PREF_USER_ID = "logged";

    private static SharedPreferences getSharedPreferences(Context ctx)
    {
        return PreferenceManager.getDefaultSharedPreferences(ctx);
    }

    public static void setUserLogged(Context ctx, boolean userLogged)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putBoolean(PREF_USER_ID, userLogged);
        editor.apply();
    }

    public static boolean getUserLogged(Context ctx)
    {
        return getSharedPreferences(ctx).getBoolean(PREF_USER_ID, false);
    }
}

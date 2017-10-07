package com.seoulsi.client.seoulro.login;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by SanJuku on 2017-10-07.
 */

public class SharedPreferenceBase {
    private static final String SHARED_PREF_NAME = "user";

    private static Context context;

    public static SharedPreferenceBase init(Context context) {
        setContext(context);
        return new SharedPreferenceBase();
    }

    public static void setContext(Context context) {
        SharedPreferenceBase.context = context;
    }

    protected static void setString(final String key, final String value) {
        getPrefs().edit().putString(key, value).commit();
    }

    protected static SharedPreferences getPrefs() {
        return context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
    }
}
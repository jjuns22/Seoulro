package com.seoulsi.client.seoulro.network;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashSet;

/**
 * Created by SanJuku on 2017-10-06.
 */

public class MySharedPreferences {
    public static final String KEY_COOKIE = "seoulro.cookie";
    private static MySharedPreferences sharedPreferences = null;

    public static MySharedPreferences getInstanceOf(Context c) {
        if (sharedPreferences == null) {
            sharedPreferences = new MySharedPreferences(c);
        }
        return sharedPreferences;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////
    private Context mContext;
    private SharedPreferences pref;

    public MySharedPreferences(Context c) {
        mContext = c;
        final String PREF_NAME = c.getPackageName();
        pref = mContext.getSharedPreferences(PREF_NAME, Activity.MODE_PRIVATE);
    }


    public void putHashSet(String key, HashSet<String> set) {
        SharedPreferences.Editor editor = pref.edit();
        editor.putStringSet(key, set);
        editor.commit();
    }


    public HashSet<String> getHashSet(String key, HashSet<String> dftValue) {
        try {
            return (HashSet<String>) pref.getStringSet(key, dftValue);
        } catch (Exception e) {
            e.printStackTrace();
            return dftValue;
        }
    }
}


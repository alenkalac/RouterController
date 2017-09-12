package com.akproductions.routercontroller2;

import android.content.Context;
import android.content.SharedPreferences;

import java.security.PrivateKey;

/**
 * Created by Alen Kalac on 12/09/2017.
 */

public class Pref {

    private static SharedPreferences sp;

    public Pref(Context context) {
        initSP(context);
    }

    public String getString(String key) {
        return sp.getString(key, "");
    }

    public void putString(String key, String value) {
        SharedPreferences.Editor edit = sp.edit();
        edit.putString(key, value);
        edit.apply();
    }

    public int getInt(String key) {
        return sp.getInt(key, 0);
    }

    public void putInt(String key, int value) {
        SharedPreferences.Editor edit = sp.edit();
        edit.putInt(key, value);
        edit.apply();
    }

    public void clearAll() {
        sp.edit().clear().apply();
    }

    private void initSP(Context context) {
        if(sp == null)
            sp = context.getSharedPreferences("MyData", Context.MODE_PRIVATE);
    }
}

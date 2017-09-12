package com.akproductions.routercontroller2;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Alen Kalac on 12/09/2017.
 */

public class Pref {

    private static SharedPreferences sp;

    public static String getString(String key) {
        initSP();
        return sp.getString(key, "");
    }

    public static void putString(String key, String value) {
        initSP();
        sp.edit().putString(key, value);
        sp.edit().apply();
    }

    public static int getInt(String key) {
        initSP();
        return sp.getInt(key, 0);
    }

    public static void putInt(String key, int value) {
        sp.edit().putInt(key, value);
        sp.edit().apply();
    }

    private static void initSP() {
        if(sp == null)
            sp = MainActivity.getContext().getSharedPreferences("MyData", Context.MODE_PRIVATE);
    }
}

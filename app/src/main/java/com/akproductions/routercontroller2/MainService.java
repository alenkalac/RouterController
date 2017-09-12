package com.akproductions.routercontroller2;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.akproductions.routercontroller2.managers.MyAlarmManager;

public class MainService extends IntentService {

    public MainService() {
        super("MainService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        //MyAlarmManager.scheduleAlarms(this);
        Log.d("MainService", "ON_HANDLE_INTENT");
    }
}
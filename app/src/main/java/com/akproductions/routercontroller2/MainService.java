package com.akproductions.routercontroller2;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

public class MainService extends IntentService {

    public MainService() {
        super("MainService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        OnTimeHandler.scheduleAlarms(this);
        Log.d("MainService", "ON_HANDLE_INTENT");
    }
}
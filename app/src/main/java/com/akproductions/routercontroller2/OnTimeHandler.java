package com.akproductions.routercontroller2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.akproductions.routercontroller2.managers.MyAlarmManager;

public class OnTimeHandler extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String extra = intent.getStringExtra("status");

        Intent intentOn = new Intent(context, TelnetService.class);
        intentOn.putExtra("status", extra);
        context.startService(intentOn);

        if(extra.equals("up"))
            MyAlarmManager.scheduleAlarms(context, MyAlarmManager.MODE.ON, true);
        else
            MyAlarmManager.scheduleAlarms(context, MyAlarmManager.MODE.OFF, true);
    }


}

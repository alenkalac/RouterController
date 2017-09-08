package com.akproductions.routercontroller2;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import java.util.Calendar;

public class OnTimeHandler extends BroadcastReceiver {
    private static final int PERIOD=1000*60;
    private static boolean running = false;

    @Override
    public void onReceive(Context context, Intent intent) {

        if(!running) {
            test(context);
            Toast.makeText(context, "STARTING NEW TIMER", Toast.LENGTH_LONG);
            Log.d("ALART", "OnRecieveTest");
        }
        else {
            Toast.makeText(context, "RUNNING", Toast.LENGTH_LONG).show();
            Log.d("ALART", "OnRecieve");
        }
    }

    static void test(Context context) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, 17);
        c.set(Calendar.MINUTE, 55);
        c.set(Calendar.SECOND, 0);

        AlarmManager mgr=
                (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent i=new Intent(context, OnTimeHandler.class);
        PendingIntent pi=PendingIntent.getBroadcast(context, 0, i, 0);
        mgr.setRepeating(AlarmManager.RTC_WAKEUP,
                c.getTimeInMillis(), PERIOD, pi);

        running = true;
    }

    static void scheduleAlarms(Context context) {
        AlarmManager mgr=
                (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent i=new Intent(context, OnTimeHandler.class);
        PendingIntent pi=PendingIntent.getBroadcast(context, 0, i, 0);
        mgr.set(AlarmManager.RTC_WAKEUP,
                System.currentTimeMillis()+1000, pi);

        Log.d("ALART", "Starting alarm");
        Toast.makeText(context, "Starting alarm", Toast.LENGTH_LONG).show();

    }

    static void cancelAlarms(Context context) {
        AlarmManager mgr=
                (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent i=new Intent(context, OnTimeHandler.class);
        PendingIntent pi=PendingIntent.getBroadcast(context, 0, i, 0);

        mgr.cancel(pi);
        Toast.makeText(context, "Killing alarm", Toast.LENGTH_LONG).show();
        Log.d("ALART", "Killing alarm");
    }
}

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
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;
import android.widget.Toast;

import android.content.SharedPreferences;

import java.util.Calendar;

public class OnTimeHandler extends WakefulBroadcastReceiver {
    private static final int PERIOD=1000*60;
    private static PendingIntent pi;

    @Override
    public void onReceive(Context context, Intent intent) {
       //TODO: Implement Restart alarm after reboot.
    }

    static void scheduleAlarms(Context context) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 25);
        c.set(Calendar.SECOND, 0);

        AlarmManager mgr=
                (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent i=new Intent(context, OnTimeHandler.class);
        PendingIntent pi=PendingIntent.getBroadcast(context, 3535, i, PendingIntent.FLAG_UPDATE_CURRENT);
        mgr.setRepeating(AlarmManager.RTC_WAKEUP,
                c.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pi);

        Toast.makeText(context, "Starting alarm", Toast.LENGTH_LONG).show();

        SharedPreferences s = context.getSharedPreferences("MyData", Context.MODE_PRIVATE);
        int start_hour = s.getInt("start_hours", 0);
        int start_minutes = s.getInt("start_minutes", 0);
        int end_hours = s.getInt("end_hours", 0);
        int end_minutes = s.getInt("end_minutes", 0);

        String message = "OFF: " + start_hour + ":" + start_minutes + " - ON: " + end_hours + ":" + end_minutes;

        MyNotificationManager.showNotification("Rounter Controller", message, context);

    }

    static void cancelAlarms(Context context) {
        AlarmManager mgr=
                (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent i=new Intent(context, OnTimeHandler.class);
        //PendingIntent pi=PendingIntent.getBroadcast(context, 0, i, 0);

        mgr.cancel(pi);
        Toast.makeText(context, "Killing alarm", Toast.LENGTH_LONG).show();
        Log.d("ALART", "Killing alarm");

        MyNotificationManager.clearAllNotifications(context);
    }
}

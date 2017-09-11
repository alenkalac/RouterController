package com.akproductions.routercontroller2;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import android.content.SharedPreferences;

import java.util.Calendar;

public class OnTimeHandler extends BroadcastReceiver {
    private static PendingIntent pi;

    @Override
    public void onReceive(Context context, Intent intent) {
       //TODO: Implement Restart alarm after reboot.
    }

    static void scheduleAlarms(Context context) {

        AlarmManager aManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, OnTimeHandler.class);
        pi=PendingIntent.getBroadcast(context, 3535, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        SharedPreferences s = context.getSharedPreferences("MyData", Context.MODE_PRIVATE);

        int start_hour = s.getInt("start_hours", 0);
        int start_minutes = s.getInt("start_minutes", 0);
        int end_hours = s.getInt("end_hours", 0);
        int end_minutes = s.getInt("end_minutes", 0);

        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 25);
        c.set(Calendar.SECOND, 0);

        aManager.setRepeating(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pi);

        Toast.makeText(context, "Starting alarm", Toast.LENGTH_LONG).show();

        String message = "OFF: " + start_hour + ":" + start_minutes + " - ON: " + end_hours + ":" + end_minutes;

        MyNotificationManager.showNotification("Router Controller", message, context);
    }

    static void cancelAlarms(Context context) {
        AlarmManager aManager =  (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);

        aManager.cancel(pi);
        Toast.makeText(context, "Killing alarm", Toast.LENGTH_LONG).show();
        //Log.d("ALART", "Killing alarm");

        MyNotificationManager.clearAllNotifications(context);
    }
}

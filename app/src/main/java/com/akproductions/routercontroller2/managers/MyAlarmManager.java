package com.akproductions.routercontroller2.managers;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;
import com.akproductions.routercontroller2.OnTimeHandler;
import com.akproductions.routercontroller2.Pref;

import java.util.Calendar;

/**
 * Created by Alen Kalac on 12/09/2017.
 */

public class MyAlarmManager {
    private static final int ID = 3535;

    public enum MODE {
        ON, OFF, BOTH
    }

    public static void scheduleAlarms(Context context, MODE mode, boolean reset) {
        Pref pref = new Pref(context);

        int start_hour = pref.getInt("start_hours");
        int start_minutes = pref.getInt("start_minutes");

        int end_hours = pref.getInt("end_hours");
        int end_minutes = pref.getInt("end_minutes");

        Calendar alarm;

        if(mode == MODE.OFF || mode == MODE.BOTH) {
            alarm = getCalendar(start_hour, start_minutes, reset);
            setAlarm(context, alarm, false);
        }
        if(mode == MODE.ON || mode == MODE.BOTH) {
            alarm = getCalendar(end_hours, end_minutes, reset);
            setAlarm(context, alarm, true);
        }

        if(!reset) {
            Toast.makeText(context, "Starting alarm", Toast.LENGTH_LONG).show();

            String message = "OFF: " + start_hour + ":" + start_minutes + " - ON: " + end_hours + ":" + end_minutes;

            MyNotificationManager.showNotification("Router Controller", message, context);
        }
    }

    public static Calendar getCalendar(int h, int m, boolean reset) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, h);
        cal.set(Calendar.MINUTE, m);
        cal.set(Calendar.SECOND, 0);
        if(reset) {
            int day = cal.get(Calendar.DAY_OF_WEEK);
            day = (day == 7) ? 1 : day+1;
            cal.set(Calendar.DAY_OF_WEEK, day);
        }

        return cal;
    }

    private static void setAlarm(Context context, Calendar cal, boolean onFlag) {
        int alarmID = ID;
        AlarmManager aManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(context, OnTimeHandler.class);
        if(onFlag)
            intent.putExtra("status", "up");
        else {
            intent.putExtra("status", "down");
            alarmID+=1;
        }

        PendingIntent pi = PendingIntent.getBroadcast(context, alarmID, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        aManager.setExact(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pi);
    }

    public static void cancelAlarms(Context context) {
        int alarmID = ID;
        AlarmManager aManager =  (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, OnTimeHandler.class);
        PendingIntent pi1 = PendingIntent.getBroadcast(context, alarmID, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        PendingIntent pi2 = PendingIntent.getBroadcast(context, alarmID+1, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        aManager.cancel(pi1);
        aManager.cancel(pi2);
        Toast.makeText(context, "Stopping Alarms", Toast.LENGTH_LONG).show();

        MyNotificationManager.clearAllNotifications(context);
    }
}

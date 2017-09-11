package com.akproductions.routercontroller2;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by Alen Kalac on 11/09/2017.
 */

public class MyNotificationManager {
    private static NotificationManager androidNotificationManager;
    private static int NOTIFICATION_ID = R.string.notification_id;

    public static void showNotification(String header, String text, Context context) {
        androidNotificationManager = (NotificationManager)context.getSystemService(NOTIFICATION_SERVICE);
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0,
                new Intent(context, MainActivity.class), 0);

        // Set the info for the views that show in the notification panel.
        Notification notification = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.ic_app_icon)
                //.setTicker("Example")  // the status text
                .setWhen(System.currentTimeMillis())  // the time stamp
                .setContentTitle(header)  // the label of the entry
                .setContentText(text)  // the contents of the entry
                .setContentIntent(contentIntent)  // The intent to send when the entry is clicked
                .build();

        // Send the notification.
        notification.flags |= Notification.FLAG_NO_CLEAR;
        androidNotificationManager.notify(NOTIFICATION_ID, notification);
    }

    public static void clearAllNotifications(Context context) {
        if(androidNotificationManager == null)
            androidNotificationManager = (NotificationManager)context.getSystemService(NOTIFICATION_SERVICE);
        androidNotificationManager.cancelAll();
    }

}

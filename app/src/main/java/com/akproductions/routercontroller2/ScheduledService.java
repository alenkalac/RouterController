package com.akproductions.routercontroller2;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Alen Kalac on 06/09/2017.
 */

public class ScheduledService extends Service {
    private NotificationManager mNM;

    // Unique Identification Number for the Notification.
    // We use it on Notification start, and to cancel it.
    //private int NOTIFICATION = R.string.local_service_started;

    /**
     * Class for clients to access.  Because we know this service always
     * runs in the same process as its clients, we don't need to deal with
     * IPC.
     */
    public class LocalBinder extends Binder {
        ScheduledService getService() {
            return ScheduledService.this;
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("LocalService", "Received start id " + startId + ": " + intent);
        super.onStartCommand(intent, flags, startId);
        OnTimeHandler.scheduleAlarms(this);

        mNM = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);

        // Display a notification about us starting.  We put an icon in the status bar.
        showNotification();

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
       // OnTimeHandler.cancelAlarms(this);
       // Intent intent = new Intent("RESTART");
      //  sendBroadcast(intent);
        // Cancel the persistent notification.
        //mNM.cancel(NOTIFICATION);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    // This is the object that receives interactions from clients.  See
    // RemoteService for a more complete example.
    private final IBinder mBinder = new LocalBinder();

    /**
     * Show a notification while this service is running.
     */
    private void showNotification() {
        // In this sample, we'll use the same text for the ticker and the expanded notification
        //CharSequence text = getText(R.string.local_service_started);

        // The PendingIntent to launch our activity if the user selects this notification
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, MainActivity.class), 0);

        // Set the info for the views that show in the notification panel.
        Notification notification = new Notification.Builder(this)
                .setSmallIcon(R.mipmap.notification)
                //.setTicker(text)  // the status text
                .setWhen(System.currentTimeMillis())  // the time stamp
                .setContentTitle("text")  // the label of the entry
                //.setContentText(text)  // the contents of the entry
                .setContentIntent(contentIntent)  // The intent to send when the entry is clicked
                .build();

        // Send the notification.
        //mNM.notify(NOTIFICATION, notification);
    }
}

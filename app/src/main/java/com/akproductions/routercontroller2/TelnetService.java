package com.akproductions.routercontroller2;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.util.Log;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class TelnetService extends IntentService {

    public TelnetService() {
        super("TelnetService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d("TelnetService", "OnHandleIntent");
        Telnet telnet = new Telnet("192.168.1.254", getString(R.string.username), getString(R.string.password));
    }


}

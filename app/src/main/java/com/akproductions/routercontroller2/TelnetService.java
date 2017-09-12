package com.akproductions.routercontroller2;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.content.SharedPreferences;
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
        String extra = intent.getStringExtra("status");
        Pref pref = new Pref(this);
        String host = pref.getString("host");
        String username = pref.getString("username");
        String password = pref.getString("password");
        Telnet telnet = new Telnet(host, username, password);
        telnet.sendCommand("adsl connection --" + extra);
    }


}

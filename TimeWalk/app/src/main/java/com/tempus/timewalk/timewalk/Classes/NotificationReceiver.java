package com.tempus.timewalk.timewalk.Classes;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by AlphaCR on 13/10/2017.
 */

public class NotificationReceiver extends BroadcastReceiver
{
    public void onReceive(final Context context, final Intent intent)
    {
        if ("com.google.android.c2dm.intent.REGISTRATION".equals(intent.getAction())) {

            //handleRegistration(context, intent); // you'll have to write this function

        } else if ("com.google.android.c2dm.intent.RECEIVE".equals(intent.getAction())) {

            // the handle message function will need to check the user's current location using the location API you choose, and then create the proper Notification if necessary.
            //handleMessage(context, intent);

        }
    }
}
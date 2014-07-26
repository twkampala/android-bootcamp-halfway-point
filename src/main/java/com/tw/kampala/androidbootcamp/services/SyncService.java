package com.tw.kampala.androidbootcamp.services;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import com.google.inject.Inject;
import com.tw.kampala.androidbootcamp.R;
import roboguice.service.RoboIntentService;

public class SyncService extends RoboIntentService {

    @Inject
    NotificationManager notificationManager;

    public SyncService() {
        super("SyncService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_launcher)
                .setContentText("Syncing...")
                .setContentTitle("Syncing...")
                .setProgress(0, 0, true);

        notificationManager.notify(1001, builder.build());

        stopSelf();
    }

}

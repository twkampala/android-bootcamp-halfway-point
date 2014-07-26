package com.tw.kampala.androidbootcamp.services;

import android.app.NotificationManager;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import com.google.common.io.ByteStreams;
import com.google.inject.Inject;
import com.tw.kampala.androidbootcamp.R;
import com.tw.kampala.androidbootcamp.models.Item;
import com.tw.kampala.androidbootcamp.models.ItemIds;
import com.tw.kampala.androidbootcamp.services.api.ItemAPI;
import retrofit.RestAdapter;
import retrofit.client.Response;
import roboguice.service.RoboIntentService;

public class SyncService extends RoboIntentService {

    public static final int NID = 1001;

    @Inject
    NotificationManager notificationManager;

    @Inject
    ItemAPI itemAPI;

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

        try {
            ItemIds itemIds = itemAPI.getItemIds();
            int counter = 0;

            builder.setProgress(itemIds.getIds().size(), counter, false);
            notificationManager.notify(NID, builder.build());

            for (String itemId: itemIds.getIds()) {
                Item item = itemAPI.getItem(itemId);
                builder.setProgress(itemIds.getIds().size(), ++counter, false);

                Response avatar = itemAPI.getAvatar(itemId);
                byte[] response = ByteStreams.toByteArray(avatar.getBody().in());

                notificationManager.notify(NID, builder.build());
            }

            builder.setContentText("Sync complete");
            notificationManager.notify(NID, builder.build());
        } catch (Exception e) {
            builder.setContentText("Sync failed!!!");
            notificationManager.notify(NID, builder.build());
        }

        stopSelf();
    }

}

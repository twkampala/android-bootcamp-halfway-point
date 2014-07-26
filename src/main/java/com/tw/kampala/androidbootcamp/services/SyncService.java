package com.tw.kampala.androidbootcamp.services;

import android.app.NotificationManager;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import com.google.inject.Inject;
import com.tw.kampala.androidbootcamp.R;
import com.tw.kampala.androidbootcamp.models.Item;
import com.tw.kampala.androidbootcamp.models.ItemIds;
import com.tw.kampala.androidbootcamp.services.api.ItemAPI;
import retrofit.RestAdapter;
import roboguice.service.RoboIntentService;

public class SyncService extends RoboIntentService {

    public static final int NID = 1001;

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

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("http://sync-server.herokuapp.com")
                .build();

        ItemAPI itemAPI = restAdapter.create(ItemAPI.class);

        ItemIds itemIds = itemAPI.getItemIds();
        int counter = 0;

        builder.setProgress(itemIds.getIds().size(), counter, false);
        notificationManager.notify(NID, builder.build());

        for (String itemId: itemIds.getIds()) {
            Item item = itemAPI.getItem(itemId);
            builder.setProgress(itemIds.getIds().size(), ++counter, false);
            notificationManager.notify(NID, builder.build());
        }

        builder.setContentText("Sync complete");
        notificationManager.notify(NID, builder.build());

        stopSelf();
    }

}

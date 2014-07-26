package com.tw.kampala.androidbootcamp;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.tw.kampala.androidbootcamp.services.api.ItemAPI;
import retrofit.RestAdapter;

public class HelloAndroidModule extends AbstractModule {

    @Override
    protected void configure() {
    }

    @Provides @Singleton
    public RestAdapter createRestAdapter() {
        return new RestAdapter.Builder()
                .setEndpoint("http://sync-server.herokuapp.com")
                .build();
    }

    @Provides @Singleton
    public ItemAPI createItemAPI(RestAdapter restAdapter) {
        return restAdapter.create(ItemAPI.class);
    }


}

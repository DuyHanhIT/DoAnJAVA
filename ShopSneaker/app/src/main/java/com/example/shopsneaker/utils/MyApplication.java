package com.example.shopsneaker.utils;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

public class MyApplication extends Application {
    public static final String CHANNEL_ID = "push_notification_id";

    @Override
    public void onCreate() {
        super.onCreate();
        initConfig();

    }
    private void initConfig() {
        java.util.Map config = new java.util.HashMap();
        config.put("cloud_name", "du7sfuuey");
        config.put("api_key","512743237357516");
        config.put("api_secret","QR8tyj6HezL9s4gWZwWTB9ffxDQ");


        com.cloudinary.android.MediaManager.init(this, config);

    }

    private void createChannelNotification(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "PustNotification",
                    NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
    }
}

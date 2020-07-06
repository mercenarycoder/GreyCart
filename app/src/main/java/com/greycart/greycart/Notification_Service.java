package com.greycart.greycart;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

public class Notification_Service extends Service {
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
       //AlarmManager alarmManager=
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}

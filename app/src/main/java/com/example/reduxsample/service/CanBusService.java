package com.example.reduxsample.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import dagger.android.DaggerService;

public class CanBusService extends DaggerService {
    public CanBusService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}

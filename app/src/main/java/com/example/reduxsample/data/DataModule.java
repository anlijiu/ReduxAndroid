package com.example.reduxsample.data;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;

import com.example.cloud.CloudGsonAdapterFactory;
import com.example.data.CanBusAgent;
import com.example.reduxsample.data.gson.AutoValueAdapterFactory;
import com.example.reduxsample.data.gson.ExclusionUnderlineStrategy;
import com.example.reduxsample.utils.PermissionUtils;
import com.f2prateek.rx.preferences2.Preference;
import com.f2prateek.rx.preferences2.RxSharedPreferences;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


import java.io.File;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import timber.log.Timber;

import static android.content.Context.MODE_PRIVATE;


@Module
public class DataModule {

    @Provides
    @Singleton
    SharedPreferences provideSharedPreferences(Context context) {
        return context.getSharedPreferences("sample", MODE_PRIVATE);
    }

    @Provides
    @Singleton
    RxSharedPreferences provideRxSharedPreferences(SharedPreferences preferences) {
        return RxSharedPreferences.create(preferences);
    }

    @Provides
    @Singleton
    @Named("test_key")
    Preference<String> provideVehicleIdPreference(RxSharedPreferences prefs) {
        return prefs.getString("test_key", "test key");
    }

    @Provides
    @Singleton
    Gson provideGson() {
        return new GsonBuilder()
                .setExclusionStrategies(new ExclusionUnderlineStrategy())
                .registerTypeAdapterFactory(AutoValueAdapterFactory.create())
                .registerTypeAdapterFactory(CloudGsonAdapterFactory.create())
                .setPrettyPrinting()
                .create();
    }

    @Provides
    @Singleton
    @Named("Root")
    File provideRootDir(Context context){
        File cacheDir;
        if ((Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable())
                && PermissionUtils.hasPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) ) {
            cacheDir = context.getExternalFilesDir("");
            Timber.e("Root in if");
        } else {
            Timber.e("Root in else");
            cacheDir = context.getFilesDir();
        }
        Timber.e("Root cacheDir is %s", cacheDir.toString());
        return cacheDir;
    }

    @Provides
    @Singleton
    @Named("Data")
    File provideDataCacheDir(@Named("Root") File dir){
        return new File(dir, "data");
    }

    @Provides
    @Singleton
    @Named("HttpCache")
    File provideHttpCacheDir(@Named("Root") File dir){
        return new File(dir, "http");
    }

    @Provides
    @Singleton
    CanBusAgent provideCanBusAgent() {
        return new CanBusAgent();
    }

}

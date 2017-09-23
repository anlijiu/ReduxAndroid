package com.example.reduxsample.di;

import android.app.Application;
import android.content.Context;

import com.example.plugin.PluginConfiguration;
import com.example.reduxsample.R;
import com.example.reduxsample.SampleApplication;
import com.example.reduxsample.redux.ReduxModule;
import com.example.reduxsample.service.CanBusService;
import com.example.reduxsample.service.CanBusServiceComponent;
import com.example.ui.ActivityLifecyclesServer;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.android.support.DaggerApplication;


@Module(includes = {
        ReduxModule.class
}, subcomponents = CanBusServiceComponent.class)
public class AppModule {

    @Provides
    @Singleton
    Context provideContext(SampleApplication application) {
        return application.getApplicationContext();
    }

    @Provides
    @Singleton
    SampleApplication provideApplication(SampleApplication application) {
        return application;
    }
}

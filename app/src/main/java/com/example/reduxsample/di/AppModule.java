package com.example.reduxsample.di;

import android.content.Context;

import com.example.reduxsample.SampleApplication;
import com.example.reduxsample.redux.ReduxModule;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;


@Module(includes = {
        ReduxModule.class
})
public class AppModule {

    @Provides
    @Singleton
    Context provideContext(SampleApplication application) {
        return application.getApplicationContext();
    }


}

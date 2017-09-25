package com.example.reduxsample.di;

import com.example.reduxsample.SampleApplication;
import com.example.reduxsample.data.DebugDataModule;
import com.example.reduxsample.service.CanBusServiceModule;
import com.example.reduxsample.ui.DebugUiModule;


import javax.inject.Singleton;

import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(modules = {
        AppModule.class,
        CanBusServiceModule.class,
        DebugDataModule.class,
        DebugUiModule.class,
        DebugBuildersModule.class,
        AndroidSupportInjectionModule.class
})
interface AppComponent extends AndroidInjector<SampleApplication>{

    @Component.Builder
    abstract class Builder extends AndroidInjector.Builder<SampleApplication> {}
}

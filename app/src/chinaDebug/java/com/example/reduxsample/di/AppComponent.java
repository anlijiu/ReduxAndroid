package com.example.reduxsample.di;

import com.example.reduxsample.SampleApplication;
import com.example.reduxsample.data.DebugDataModule;
import com.example.reduxsample.modules.count.di.CounterBuildersModule;
import com.example.reduxsample.modules.count.di.CounterModule;
import com.example.reduxsample.modules.user.UserModule;
import com.example.reduxsample.modules.user.di.UserBuildersModule;


import javax.inject.Singleton;

import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(modules = {
        AppModule.class,
        UserModule.class,
//        UserBuildersModule.class,
        CounterModule.class,
//        CounterBuildersModule.class,
        DebugDataModule.class,
        DebugBuildersModule.class,
//        DebugUiModule.class,
        AndroidSupportInjectionModule.class
})
interface AppComponent extends AndroidInjector<SampleApplication>{

    @Component.Builder
    abstract class Builder extends AndroidInjector.Builder<SampleApplication> {}
}

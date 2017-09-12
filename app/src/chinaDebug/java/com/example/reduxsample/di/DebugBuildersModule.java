package com.example.reduxsample.di;

import com.example.di.scope.ScreenScope;
import com.example.reduxsample.MainActivity;

import com.example.reduxsample.modules.count.di.CounterBuildersModule;
import com.example.reduxsample.modules.user.UserModule;
import com.example.reduxsample.modules.user.di.UserBuildersModule;
import com.example.reduxsample.ui.DebugUiModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module()
public abstract class DebugBuildersModule extends BuildersModule {
    @ScreenScope
    @ContributesAndroidInjector(modules = {DebugUiModule.class,
//            UserModule.class,
            UserBuildersModule.class,
//            CounterModule.class,
            CounterBuildersModule.class,
    })
    abstract MainActivity mainActivityInjector();

}

package com.example.reduxsample.di;

import com.example.di.scope.ScreenScope;
import com.example.reduxsample.MainActivity;

import com.example.reduxsample.MainModule;
import com.example.reduxsample.modules.count.di.CounterBuildersModule;
import com.example.reduxsample.modules.count.di.CounterComponent;
import com.example.reduxsample.modules.count.di.CounterModule;
import com.example.reduxsample.modules.menu.di.MenuBuildersModule;
import com.example.reduxsample.modules.menu.di.MenuComponent;
import com.example.reduxsample.modules.menu.di.MenuModule;
import com.example.reduxsample.modules.user.di.UserBuildersModule;
import com.example.reduxsample.modules.user.di.UserComponent;
import com.example.reduxsample.modules.user.di.UserModule;
import com.example.reduxsample.ui.DebugUiModule;
import com.example.reduxsample.MainComponent;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module(subcomponents = { MainComponent.class, MenuComponent.class, UserComponent.class, CounterComponent.class})
public abstract class DebugBuildersModule extends BuildersModule {
    @ScreenScope
    @ContributesAndroidInjector(modules = {
            MainModule.class,
            MenuModule.class,
            UserModule.class,
            CounterModule.class,
            MenuBuildersModule.class,
            UserBuildersModule.class,
            CounterBuildersModule.class,
    })
    abstract MainActivity mainActivityInjector();

}

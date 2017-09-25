package com.example.reduxsample.modules.menu.di;


import com.example.di.scope.ScreenScope;
import com.example.reduxsample.modules.menu.MenuLeftFragment;
import com.example.reduxsample.modules.menu.MenuRightFragment;

import javax.inject.Singleton;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;


@Module
public abstract class MenuBuildersModule {
    @ContributesAndroidInjector
    abstract MenuLeftFragment leftFramgentInjector();

    @ContributesAndroidInjector
    abstract MenuRightFragment rightFramgentInjector();
}

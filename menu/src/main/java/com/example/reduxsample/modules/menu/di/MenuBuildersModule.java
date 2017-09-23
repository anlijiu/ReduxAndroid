package com.example.reduxsample.modules.count.di;


import com.example.di.scope.ScreenScope;
import com.example.reduxsample.modules.count.CounterFragment;
import com.example.reduxsample.modules.count.JustReadFragment;

import javax.inject.Singleton;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;


@Module
public abstract class CounterBuildersModule {
    @ContributesAndroidInjector
    abstract CounterFragment counterFramgentInjector();

    @ContributesAndroidInjector
    abstract JustReadFragment justReadFramgentInjector();
}

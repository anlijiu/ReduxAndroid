package com.example.reduxsample;

import com.example.reduxsample.modules.user.di.UserModule;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;

@Subcomponent(modules = { MainModule.class })
public interface MainComponent extends AndroidInjector<MainActivity> {
    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<MainActivity> {}
}
package com.example.reduxsample.service;


import dagger.Subcomponent;
import dagger.android.AndroidInjector;

@Subcomponent(modules = { CanBusServiceModule.class })
public interface CanBusServiceComponent extends AndroidInjector<CanBusService> {
    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<CanBusService> {}
}
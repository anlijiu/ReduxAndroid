package com.example.reduxsample.modules.count.di;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;
import dagger.android.DaggerFragment;

@Subcomponent(
    modules = CounterModule.class
)
public interface CounterComponent extends AndroidInjector<DaggerFragment> {

    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<DaggerFragment> {
    }
}

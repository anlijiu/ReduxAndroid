package com.example.reduxsample.modules.menu.di;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;
import dagger.android.DaggerFragment;

@Subcomponent(
    modules = MenuModule.class
)
public interface MenuComponent extends AndroidInjector<DaggerFragment> {

    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<DaggerFragment> {
    }
}

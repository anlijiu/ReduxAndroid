package com.example.reduxsample.modules.user.di;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;
import dagger.android.DaggerFragment;

@Subcomponent(
    modules = UserModule.class
)
public interface UserComponent extends AndroidInjector<DaggerFragment> {

    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<DaggerFragment> {
    }
}

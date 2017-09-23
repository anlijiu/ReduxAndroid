package com.example.reduxsample.ui;


import com.example.di.scope.ScreenScope;
import com.example.reduxsample.ui.debug.DebugViewContainer;
import com.example.ui.ViewContainer;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.android.ContributesAndroidInjector;
import dagger.multibindings.IntoMap;

@Module(includes = UiModule.class)
public final class DebugUiModule {

    @Provides
    @Singleton
    ViewContainer provideAppContainer(DebugViewContainer appContainer) {
        return appContainer;
    }

}

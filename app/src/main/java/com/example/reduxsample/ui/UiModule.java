package com.example.reduxsample.ui;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.plugin.PluginConfiguration;
import com.example.reduxsample.R;
import com.example.ui.ActivityLifecyclesServer;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by anlijiu on 17-9-13.
 */

@Module
public class UiModule {

    @Provides
    @Singleton
    ActivityLifecyclesServer.Proxy provideActivityHierarchyServer() {
        final ActivityLifecyclesServer.Proxy proxy = new ActivityLifecyclesServer.Proxy();
        return proxy;
    }

    @Provides
    @Singleton
    PluginConfiguration providePluginConfiguration(ActivityLifecyclesServer.Proxy proxy) {
        return PluginConfiguration.builder()
                .leftContainerId(R.id.container_left)
                .rightContainerId(R.id.container_right)
                .callbacksAdapterProxy(proxy)
                .build();
    }

}

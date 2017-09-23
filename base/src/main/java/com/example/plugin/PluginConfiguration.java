package com.example.plugin;

import android.content.Context;
import android.support.annotation.IntDef;
import android.support.v4.app.FragmentManager;

import com.example.ui.ActivityLifecyclesServer;
import com.google.auto.value.AutoValue;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.annotation.Nullable;

import io.reactivex.Flowable;
import io.reactivex.subjects.Subject;

/**
 * Created by anlijiu on 17-9-13.
 */
//INDICATOR_AREA, MENU_AREA, B13_AREA, METER_AREA, APP_AREA, APP_SHORTCUT_AREA;
@AutoValue
public abstract class PluginConfiguration {

    public abstract HostDelegate hostDelegate();

    public abstract int leftContainerId();
    public abstract int rightContainerId();
    public abstract Context context();
    public abstract FragmentManager fragmentManager();
    public abstract ActivityLifecyclesServer.Proxy callbacksAdapterProxy();
    public abstract Flowable<String> hostUIStateStream();

    public static PluginConfiguration create(HostDelegate hostDelegate, int leftContainerId, int rightContainerId, Context context, FragmentManager fragmentManager, ActivityLifecyclesServer.Proxy callbacksAdapterProxy, Flowable<String> hostUIStateStream) {
        return builder()
                .hostDelegate(hostDelegate)
                .leftContainerId(leftContainerId)
                .rightContainerId(rightContainerId)
                .context(context)
                .fragmentManager(fragmentManager)
                .callbacksAdapterProxy(callbacksAdapterProxy)
                .hostUIStateStream(hostUIStateStream)
                .build();
    }

    public static Builder builder() {
        return new AutoValue_PluginConfiguration.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder leftContainerId(int leftContainerId);

        public abstract Builder rightContainerId(int rightContainerId);

        public abstract Builder callbacksAdapterProxy(ActivityLifecyclesServer.Proxy callbacksAdapterProxy);

        public abstract Builder context(Context context);

        public abstract Builder fragmentManager(FragmentManager fragmentManager);

        public abstract Builder hostUIStateStream(Flowable<String> hostUIStateStream);

        public abstract Builder hostDelegate(HostDelegate hostDelegate);

        public abstract PluginConfiguration build();
    }
}

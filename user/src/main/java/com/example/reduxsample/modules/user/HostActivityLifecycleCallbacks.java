package com.example.reduxsample.modules.user;

import android.app.Activity;
import android.app.Application.ActivityLifecycleCallbacks;
import android.os.Bundle;

import com.example.ui.ActivityLifecyclesServer;

import timber.log.Timber;

/**
 * Created by anlijiu on 17-9-13.
 */

public class HostActivityLifecycleCallbacks implements ActivityLifecyclesServer {
    @Override
    public void onActivityCreated(Activity activity, Bundle bundle) {
        Timber.e("onActivityCreated");
    }

    @Override
    public void onActivityStarted(Activity activity) {
        Timber.e("onActivityStarted");
    }

    @Override
    public void onActivityResumed(Activity activity) {
        Timber.e("onActivityResumed");
    }

    @Override
    public void onActivityPaused(Activity activity) {
        Timber.e("onActivityPaused");
    }

    @Override
    public void onActivityStopped(Activity activity) {
        Timber.e("onActivityStopped");
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
        Timber.e("onActivitySaveInstanceState");
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        Timber.e("onActivityDestroyed");
    }
}

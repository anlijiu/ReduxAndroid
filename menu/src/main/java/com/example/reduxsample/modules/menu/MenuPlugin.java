package com.example.reduxsample.modules.count;

import android.app.Activity;
import android.app.Application;
import android.support.v4.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;

import com.example.di.scope.ScreenScope;
import com.example.plugin.HostDelegate;
import com.example.plugin.Plugin;
import com.example.plugin.PluginConfiguration;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import timber.log.Timber;

public class CounterPlugin implements Plugin, HostDelegate.OnSelectMenuChangeListener {

    HostDelegate hostDelegate;
    Context context;

    public CounterPlugin(@ScreenScope Context context, HostDelegate hostDelegate) {
        this.hostDelegate = hostDelegate;
        this.context = context;
        hostDelegate.addOnMenuClickListener(this);
    }

    @Override
    public void load() {

    }

    @Override
    public void unload() {
        hostDelegate.removeOnMenuClickListener(this);
    }

    @Override
    public void onSelectMenuChanged(@HostDelegate.Menu int menuItem) {
        if(HostDelegate.Menu.LIGHT == menuItem) {
            Fragment counterFragment = Fragment.instantiate(context, CounterFragment.class.getName());
            Fragment justReadFragment = Fragment.instantiate(context, JustReadFragment.class.getName());
            hostDelegate.show( counterFragment, HostDelegate.Position.B13_AREA);
            hostDelegate.show(justReadFragment, HostDelegate.Position.APP_SHORTCUT_AREA);
        }
    }
}

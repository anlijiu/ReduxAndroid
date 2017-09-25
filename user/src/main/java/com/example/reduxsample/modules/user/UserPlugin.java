package com.example.reduxsample.modules.user;

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

public class UserPlugin implements Plugin, HostDelegate.OnSelectMenuChangeListener {

    HostDelegate hostDelegate;
    Context context;

    public UserPlugin(@ScreenScope Context context, HostDelegate hostDelegate) {
        this.hostDelegate = hostDelegate;
        this.context = context;
        hostDelegate.addOnMenuClickListener(this);
        Timber.d("hostDelegate is %s", hostDelegate);
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
        Timber.d("UserPlugin onSelectMenuChanged , menuItem is %d", menuItem);
        if(HostDelegate.Menu.CLIMATE == menuItem) {
            Fragment userListFragment = Fragment.instantiate(context, UserListFragment.class.getName());
            Fragment userFragment = Fragment.instantiate(context, UserFragment.class.getName());
            hostDelegate.show( userListFragment, HostDelegate.Position.B13_AREA);
            hostDelegate.show(userFragment, HostDelegate.Position.APP_SHORTCUT_AREA);
        }
    }
}

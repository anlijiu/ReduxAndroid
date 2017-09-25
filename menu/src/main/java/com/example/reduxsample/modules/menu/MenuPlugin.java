package com.example.reduxsample.modules.menu;

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

public class MenuPlugin implements Plugin, HostDelegate.OnSelectMenuChangeListener {

    HostDelegate hostDelegate;
    Context context;

    public MenuPlugin(@ScreenScope Context context, HostDelegate hostDelegate) {
        this.hostDelegate = hostDelegate;
        this.context = context;
        hostDelegate.addOnMenuClickListener(this);
    }

    @Override
    public void load() {
        Fragment leftMenuFragment = Fragment.instantiate(context, MenuLeftFragment.class.getName());
        Fragment rightMenuFragment = Fragment.instantiate(context, MenuRightFragment.class.getName());
        hostDelegate.show(leftMenuFragment, HostDelegate.Position.LEFT_MENU);
        hostDelegate.show(rightMenuFragment, HostDelegate.Position.RIGHT_MENU);
    }

    @Override
    public void unload() {
        hostDelegate.removeOnMenuClickListener(this);
    }

    @Override
    public void onSelectMenuChanged(@HostDelegate.Menu int menuItem) {
//        if(HostDelegate.Menu.LIGHT == menuItem) {
//            Fragment menuerFragment = Fragment.instantiate(context, MenuLeftFragment.class.getName());
//            Fragment justReadFragment = Fragment.instantiate(context, MenuRightFragment.class.getName());
//            hostDelegate.show( menuerFragment, HostDelegate.Position.B13_AREA);
//            hostDelegate.show(justReadFragment, HostDelegate.Position.APP_SHORTCUT_AREA);
//        }
    }

    public void selectMenu(@HostDelegate.Menu int menuItem) {
        hostDelegate.selectMenu(menuItem);
        Timber.d("hostDelegate is %s", hostDelegate);
    }
}


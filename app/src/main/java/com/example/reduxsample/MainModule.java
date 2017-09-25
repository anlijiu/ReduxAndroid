package com.example.reduxsample;

import android.app.Activity;
import android.app.Application;
import android.app.Dialog;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.SparseArray;
import android.view.View;

import com.example.plugin.HostDelegate;
import com.example.plugin.PluginConfiguration;
import com.example.reduxsample.R;
import com.example.reduxsample.SampleApplication;
import com.example.reduxsample.redux.ReduxModule;
import com.example.statemachine.StateMachine;
import com.example.statemachine.StateMachineBuilder;
import com.example.ui.ActivityLifecyclesServer;
import com.example.di.scope.ScreenScope;
import com.example.ui.misc.FlexibleToast;


import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

@Module
public class MainModule {
//    @Provides
//    @ScreenScope
//    Context provideContext(MainActivity activity) {
//        return activity;
//    }

    @Provides
    @ScreenScope
    @Named("hostContainers")
    SparseArray<Integer> provideHostContainers() {
        SparseArray<Integer> containerIdPositions = new SparseArray<>();
        containerIdPositions.append(HostDelegate.Position.LEFT_MENU, R.id.left_menu_container);
        containerIdPositions.append(HostDelegate.Position.RIGHT_MENU, R.id.right_menu_container);
        containerIdPositions.append(HostDelegate.Position.B13_AREA, R.id.container_left);
        containerIdPositions.append(HostDelegate.Position.APP_SHORTCUT_AREA, R.id.container_right);
        return containerIdPositions;
    }

    @Provides
    @ScreenScope
    HostDelegate.Layout provideHostDelegateLayout(SampleApplication application, MainActivity activity, @Named("hostContainers") SparseArray<Integer> containers) {
        return new HostDelegate.Layout() {
            @Override
            public void show(View view, @HostDelegate.Position int position) {
            }

            @Override
            public void show(Fragment fragment, @HostDelegate.Position int position) {
                Fragment currentFragment = activity.getSupportFragmentManager().findFragmentById(containers.get(position));
                FragmentTransaction ft = activity.getSupportFragmentManager().beginTransaction();
                if(currentFragment == null || !currentFragment.isAdded()) {
                    if(currentFragment != null) {
                        ft.hide(currentFragment);
                    }
                    ft.add(containers.get(position), fragment);
                } else {
                    ft.hide(currentFragment).show(fragment);
                }

                ft.commit();
            }

            @Override
            public void showToastByBuilder(FlexibleToast.Builder builder) {
                application.showToastByBuilder(builder);
            }

            @Override
            public void popup(Dialog dialog) {

            }
        };
    }

    @Provides
    @ScreenScope
    StateMachine<HostDelegate.PowerModeState, HostDelegate.PowerModeEvent> providePowerModeStateMachine() {
        return new StateMachineBuilder<HostDelegate.PowerModeState, HostDelegate.PowerModeEvent>(HostDelegate.PowerModeState.ON)
                        .addTransition(HostDelegate.PowerModeState.ON, HostDelegate.PowerModeEvent.OFF, HostDelegate.PowerModeState.OFF)
                        .addTransition(HostDelegate.PowerModeState.OFF, HostDelegate.PowerModeEvent.ON, HostDelegate.PowerModeState.ON)
                        .build();
    }

    @Provides
    @ScreenScope
    HostDelegate provideHostDelegate(HostDelegate.Layout layout, StateMachine<HostDelegate.PowerModeState, HostDelegate.PowerModeEvent> stateMachine) {
        return new HostDelegate() {
            @Override
            public Layout layout() {
                return layout;
            }

            @Override
            public StateMachine<PowerModeState, PowerModeEvent> powerModeStateMachine() {
                return stateMachine;
            }
        };
    }
}

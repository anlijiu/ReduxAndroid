package com.example.reduxsample.ui.debug;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.PowerManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.di.scope.ScreenScope;
import com.example.reduxsample.R;
import com.example.reduxsample.models.AppState;
import com.example.ui.ViewContainer;
import com.yheriatovych.reductor.Store;

import javax.inject.Inject;
import javax.inject.Singleton;

import butterknife.BindView;
import butterknife.ButterKnife;


@Singleton
public final class DebugViewContainer implements ViewContainer {
    @Inject Store<AppState> store;
    static class ViewHolder {
        @BindView(R.id.debug_drawer_layout) DrawerLayout drawerLayout;
        @BindView(R.id.debug_drawer) ViewGroup debugDrawer;
        @BindView(R.id.debug_content) ViewGroup content;
    }

    @Inject
    public DebugViewContainer() {
    }

    @Override
    public ViewGroup forActivity(final Activity activity) {
        activity.setContentView(R.layout.debug_activity_frame);
        final ViewHolder viewHolder = new ViewHolder();
        ButterKnife.bind(viewHolder, activity);

        final Context drawerContext = new ContextThemeWrapper(activity, R.style.AppTheme);
        final DebugView debugView = new DebugView(drawerContext, store);
        viewHolder.debugDrawer.addView(debugView);

        viewHolder.drawerLayout.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerOpened(View drawerView) {
                debugView.onDrawerOpened();
            }
        });

        return viewHolder.content;
    }
}

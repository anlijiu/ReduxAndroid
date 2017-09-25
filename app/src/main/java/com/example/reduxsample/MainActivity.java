package com.example.reduxsample;

import android.app.Dialog;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.plugin.HostDelegate;
import com.example.plugin.PluginConfiguration;
import com.example.reduxsample.base.BaseActivity;
import com.example.reduxsample.models.AppState;
import com.example.reduxsample.modules.count.CounterFragment;
import com.example.reduxsample.modules.count.CounterPlugin;
import com.example.reduxsample.modules.count.JustReadFragment;
import com.example.reduxsample.modules.displayLogic.DisplayLogicActions;
import com.example.reduxsample.modules.displayLogic.DisplayLogicState;
import com.example.reduxsample.modules.menu.MenuPlugin;
import com.example.reduxsample.modules.user.UserFragment;
import com.example.reduxsample.modules.user.UserListFragment;
import com.example.reduxsample.modules.user.UserPlugin;
import com.example.statemachine.StateMachine;
import com.example.statemachine.StateMachineBuilder;
import com.example.ui.ActivityLifecyclesServer;
import com.example.ui.Constants;
import com.example.ui.misc.FlexibleToast;
import com.github.mzule.activityrouter.annotation.Router;
import com.yheriatovych.reductor.Actions;
import com.yheriatovych.reductor.Cancelable;
import com.yheriatovych.reductor.Cursor;
import com.yheriatovych.reductor.Cursors;
import com.yheriatovych.reductor.Store;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.BackpressureStrategy;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

public class MainActivity extends BaseActivity {

    @Inject
    UserPlugin userPlugin;
    @Inject
    CounterPlugin counterPlugin;
    @Inject
    MenuPlugin menuPlugin;
    @Inject
    HostDelegate hostDelegate;
    @Inject
    ActivityLifecyclesServer.Proxy proxy;


    @Inject
    Store<AppState> store;

    @Override
    protected int layoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadPlugins();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        userPlugin.unload();
        counterPlugin.unload();
        menuPlugin.unload();
        super.onDestroy();
    }


    private void loadPlugins() {
        userPlugin.load();
        counterPlugin.load();
        menuPlugin.load();
    }
}

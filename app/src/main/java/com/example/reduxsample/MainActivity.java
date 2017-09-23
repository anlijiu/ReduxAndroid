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
    ActivityLifecyclesServer.Proxy proxy;


    @Inject
    Store<AppState> store;

    @BindView(R.id.tbtn_1)
    ToggleButton tbtn1;
    @BindView(R.id.tbtn_2)
    ToggleButton tbtn2;
    @BindView(R.id.tbtn_3)
    ToggleButton tbtn3;
    @BindView(R.id.tbtn_4)
    ToggleButton tbtn4;

    Cancelable mCancelable;
    DisplayLogicActions displayLogicActions;
    Cursor<DisplayLogicState> displayLogicCursor;

    SparseArray<ToggleButton> tbtns = new SparseArray<>();
    SparseArray<String> containers = new SparseArray<>();
    SparseArray<String> uiStateArray = new SparseArray<>();

    Subject<String> hostUIStateStream;

    @Override
    protected int layoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hostUIStateStream = PublishSubject.<String>create().toSerialized();
        loadPlugins();

        displayLogicActions = Actions.from(DisplayLogicActions.class);
        displayLogicCursor = Cursors.map(store, state -> state.displayLogic());

        containers.append(R.id.container_left, Constants.container_left);
        containers.append(R.id.container_right, Constants.container_right);

        tbtns.append(R.id.tbtn_1, tbtn1);
        tbtns.append(R.id.tbtn_2, tbtn2);
        tbtns.append(R.id.tbtn_3, tbtn3);
        tbtns.append(R.id.tbtn_4, tbtn4);

        uiStateArray.append(0, "");
        uiStateArray.append(R.id.tbtn_1, "leftTop");
        uiStateArray.append(R.id.tbtn_2, "leftButtom");
        uiStateArray.append(R.id.tbtn_3, "rightTop");
        uiStateArray.append(R.id.tbtn_4, "rightTop");
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mCancelable = displayLogicCursor.subscribe(state -> {
            changeMainState(state.selectId(), state.byUser());
        });
        store.dispatch(displayLogicActions.selectMainMenuItem(0, false));
//        Bundle extras = getIntent().getExtras();
//        if(extras != null) {
//            String containerName = extras.getString("containerName");
//            String componentName = extras.getString("componentName");
//
//            int containerId = 0;
//            Timber.e("containerName is %s", containerName);
//            Timber.e("componentName is %s", componentName);
//            for (int i = 0; i < containers.size(); i++) {
//                if (containers.valueAt(i).equals(containerName)) {
//                    containerId = containers.keyAt(i);
//                    break;
//                }
//            }
//            if (!TextUtils.isEmpty(containerName) && !TextUtils.isEmpty(componentName) && containerId != 0) {
//                Fragment fragment = Fragment.instantiate(this, componentName);
//                if(!fragment.isAdded()) {
//                    Fragment currentFragment = fragmentManager.findFragmentById(containerId);
//                    fragmentManager.beginTransaction().add(containerId, fragment).hide(currentFragment).commit();
//                }
//            }
//        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        mCancelable.cancel();
    }

    @Override
    protected void onDestroy() {
        userPlugin.unload();
        counterPlugin.unload();
        super.onDestroy();
    }

    @OnClick({R.id.tbtn_1, R.id.tbtn_2, R.id.tbtn_3, R.id.tbtn_4})
    void onClick(ToggleButton view) {
        store.dispatch(displayLogicActions.selectMainMenuItem(view.getId(), true));
    }

    private void loadPlugins() {
        userPlugin.load();
        counterPlugin.load();
    }

    private void changeMainState(int selectId, boolean byUser) {
        hostUIStateStream.onNext(uiStateArray.get(selectId));
        for(int i = 0; i < tbtns.size(); i++) {
            if(tbtns.keyAt(i) == selectId) {
                tbtns.valueAt(i).setChecked(true);
            } else {
                tbtns.valueAt(i).setChecked(false);
            }
        }
    }

}

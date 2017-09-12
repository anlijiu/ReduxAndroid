package com.example.reduxsample;

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

import com.example.reduxsample.base.BaseActivity;
import com.example.reduxsample.models.AppState;
import com.example.reduxsample.modules.count.CounterFragment;
import com.example.reduxsample.modules.count.JustReadFragment;
import com.example.reduxsample.modules.displayLogic.DisplayLogicActions;
import com.example.reduxsample.modules.displayLogic.DisplayLogicState;
import com.example.reduxsample.modules.user.UserFragment;
import com.example.reduxsample.modules.user.UserListFragment;
import com.example.ui.Constants;
import com.github.mzule.activityrouter.annotation.Router;
import com.yheriatovych.reductor.Actions;
import com.yheriatovych.reductor.Cancelable;
import com.yheriatovych.reductor.Cursor;
import com.yheriatovych.reductor.Cursors;
import com.yheriatovych.reductor.Store;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import timber.log.Timber;

@Router(value = "home/:componentName/:containerName", intParams = "container")
public class MainActivity extends BaseActivity {

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
    FragmentManager fragmentManager;

    @Override
    protected int layoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentManager = this.getSupportFragmentManager();

        Fragment userList = Fragment.instantiate(this, Constants.component_user_list);
        Fragment user = Fragment.instantiate(this, Constants.component_user);
        Fragment counter = Fragment.instantiate(this, Constants.component_counter);
        Fragment justRead = Fragment.instantiate(this, Constants.component_just_read);
        Timber.e("MainActivity onCreate UserListFragment  is %s, %s, %s, %s", userList, user, counter, justRead);
        if(savedInstanceState != null) {
        } else {
            fragmentManager.beginTransaction()
                    .add(R.id.container_left, Fragment.instantiate(this, Constants.component_user_list), Constants.component_user_list)
                    .add(R.id.container_right, Fragment.instantiate(this, Constants.component_user), Constants.component_user)
                    .add(R.id.container_left, Fragment.instantiate(this, Constants.component_counter), Constants.component_counter)
                    .add(R.id.container_right, Fragment.instantiate(this, Constants.component_just_read), Constants.component_just_read)
                    .commit();
        }

        displayLogicActions = Actions.from(DisplayLogicActions.class);
        displayLogicCursor = Cursors.map(store, state -> state.displayLogic());

        containers.append(R.id.container_left, Constants.container_left);
        containers.append(R.id.container_right, Constants.container_right);

        tbtns.append(R.id.tbtn_1, tbtn1);
        tbtns.append(R.id.tbtn_2, tbtn2);
        tbtns.append(R.id.tbtn_3, tbtn3);
        tbtns.append(R.id.tbtn_4, tbtn4);
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
        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            String containerName = extras.getString("containerName");
            String componentName = extras.getString("componentName");

            int containerId = 0;
            Timber.e("containerName is %s", containerName);
            Timber.e("componentName is %s", componentName);
            for (int i = 0; i < containers.size(); i++) {
                if (containers.valueAt(i).equals(containerName)) {
                    containerId = containers.keyAt(i);
                    break;
                }
            }
            if (!TextUtils.isEmpty(containerName) && !TextUtils.isEmpty(componentName) && containerId != 0) {
                Fragment fragment = Fragment.instantiate(this, componentName);
                if(!fragment.isAdded()) {
                    Fragment currentFragment = fragmentManager.findFragmentById(containerId);
                    fragmentManager.beginTransaction().add(containerId, fragment).hide(currentFragment).commit();
                }
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        mCancelable.cancel();
    }

    @OnClick({R.id.tbtn_1, R.id.tbtn_2, R.id.tbtn_3, R.id.tbtn_4})
    void onClick(ToggleButton view) {
        store.dispatch(displayLogicActions.selectMainMenuItem(view.getId(), true));
    }

    private void changeMainState(int selectId, boolean byUser) {
        Fragment counterFragment = fragmentManager.findFragmentByTag(Constants.component_counter);
        Fragment justReadFragment = fragmentManager.findFragmentByTag(Constants.component_just_read);
        Fragment userListFragment = fragmentManager.findFragmentByTag(Constants.component_user_list);
        Fragment userFragment = fragmentManager.findFragmentByTag(Constants.component_user);

        if(0 == selectId) {
            Fragment currentLeftFragment = fragmentManager.findFragmentById(R.id.container_left);
            Fragment currentRightFragment = fragmentManager.findFragmentById(R.id.container_right);
            FragmentTransaction ft = fragmentManager.beginTransaction();
            ft.hide(userListFragment)
                    .hide(userFragment)
                    .hide(currentLeftFragment)
                    .hide(currentRightFragment)
                    .show(counterFragment)
                    .show(justReadFragment)

                    .commit();

        } else if(R.id.tbtn_1 == selectId) {
            fragmentManager.beginTransaction()
                    .hide(counterFragment)
                    .hide(justReadFragment)
                    .show(userListFragment)
                    .show(userFragment)
                    .commit();
        } else {
            fragmentManager.beginTransaction()
                    .hide(counterFragment)
                    .hide(justReadFragment)
                    .hide(userListFragment)
                    .hide(userFragment)
                    .commit();
        }
        for(int i = 0; i < tbtns.size(); i++) {
            if(tbtns.keyAt(i) == selectId) {
                tbtns.valueAt(i).setChecked(true);
            } else {
                tbtns.valueAt(i).setChecked(false);
            }
        }
    }
}

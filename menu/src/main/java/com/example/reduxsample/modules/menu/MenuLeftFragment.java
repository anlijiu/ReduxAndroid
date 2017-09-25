package com.example.reduxsample.modules.menu;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.plugin.HostDelegate;
import com.example.reduxsample.modules.menu.R;
import com.example.reduxsample.modules.menu.R2;
import com.example.reduxsample.modules.menu.base.BaseFragment;
import com.example.ui.Constants;
import com.github.mzule.activityrouter.router.Routers;
import com.yheriatovych.reductor.Actions;
import com.yheriatovych.reductor.Cancelable;
import com.yheriatovych.reductor.Cursor;
import com.yheriatovych.reductor.Cursors;
import com.yheriatovych.reductor.StateChangeListener;
import com.yheriatovych.reductor.Store;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import dagger.android.support.AndroidSupportInjection;
import timber.log.Timber;


public class MenuLeftFragment extends BaseFragment {
    public static final String TAG = MenuLeftFragment.class.getSimpleName();
    private static final int MSG_WHAT_AUTO_INCREASE = 0;
    private static final int MSG_WHAT_AUTO_INCREASE_START = 1;
    private static final int MSG_WHAT_AUTO_INCREASE_STOP = 2;

    @Inject
    Store<MenuState> store;
    @Inject
    MenuPlugin menuPlugin;

    @BindView(R2.id.tbtn_1)
    ToggleButton tbtn1;
    @BindView(R2.id.tbtn_2)
    ToggleButton tbtn2;


    private MenuActions menuActions;
    private Cursor<MenuState> menuCursor;
    private Cancelable menuCancelable;

    private HandlerThread handlerThread;
    private Handler handler;

    public static MenuLeftFragment newInstance() {
        return new MenuLeftFragment();
    }

    @Override
    public int layoutId() {
        return R.layout.fragment_menu_left;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Timber.e("MenuFragment store is %s", store);
        menuActions = Actions.from(MenuActions.class);
        menuCursor = Cursors.map(store, state -> state);
        menuCancelable = menuCursor.subscribe(state -> {
            changeSelectedItem(state.selectId(), state.byUser());
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        menuCancelable.cancel();
    }

    private void changeSelectedItem(int selectId, boolean byUser) {
        Timber.d(" changeSelectedItem selectId is %d, tbtn_1 is %d, tbtn_2 is %d", selectId, R.id.tbtn_1, R.id.tbtn_2);
        if(selectId == R.id.tbtn_1) {
            menuPlugin.selectMenu(HostDelegate.Menu.CLIMATE);
        } else if(selectId == R.id.tbtn_2) {
            menuPlugin.selectMenu(HostDelegate.Menu.LIGHT);
        }
    }

    @OnClick({R2.id.tbtn_1, R2.id.tbtn_2})
    void onClick(ToggleButton view) {
        Timber.d(" onClick view.getId is %d, tbtn_1 is %d, tbtn_2 is %d ", view.getId(), R.id.tbtn_1, R.id.tbtn_2);
        store.dispatch(menuActions.select(view.getId(), true));
    }
}

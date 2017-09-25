package com.example.reduxsample.modules.menu;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ToggleButton;


import com.example.reduxsample.modules.menu.R;
import com.example.reduxsample.modules.menu.R2;
import com.example.data.CanBusAgent;
import com.example.reduxsample.modules.menu.base.BaseFragment;
import com.yheriatovych.reductor.Actions;
import com.yheriatovych.reductor.Cancelable;
import com.yheriatovych.reductor.Cursor;
import com.yheriatovych.reductor.Cursors;
import com.yheriatovych.reductor.Store;


import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import dagger.android.support.AndroidSupportInjection;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import timber.log.Timber;


public class MenuRightFragment extends BaseFragment {
    public static final String TAG = MenuRightFragment.class.getSimpleName();

    @Inject
    Store<MenuState> store;

    @Inject
    CanBusAgent agent;

    @BindView(R2.id.tbtn_3)
    ToggleButton tbtn3;
    @BindView(R2.id.tbtn_4)
    ToggleButton tbtn4;

    private MenuActions menuActions;
    private Cursor<MenuState> menuCursor;
    private Cancelable menuCancelable;

    public static MenuRightFragment newInstance() {
        return new MenuRightFragment();
    }

    @Override
    public int layoutId() {

        return R.layout.fragment_menu_right;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Timber.e("MenuRightFragment store is %s", store);
        menuActions = Actions.from(MenuActions.class);
        menuCursor = Cursors.map(store, state -> state);

        menuCancelable = menuCursor.subscribe(state -> {
            changeSelectedItem(state.selectId(), state.byUser());
        });
    }

    @OnClick({R2.id.tbtn_3, R2.id.tbtn_4})
    void onClick(ToggleButton view) {
        store.dispatch(menuActions.select(view.getId(), true));
    }

    private void changeSelectedItem(int selectId, boolean byUser) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        menuCancelable.cancel();
    }
}

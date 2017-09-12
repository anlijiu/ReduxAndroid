package com.example.reduxsample.ui.debug;


import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import com.example.reduxsample.R;
import com.example.reduxsample.models.AppState;
import com.example.reduxsample.modules.displayLogic.DisplayLogicState;
import com.example.reduxsample.modules.user.UserState;
import com.yheriatovych.reductor.Cancelable;
import com.yheriatovych.reductor.Cursors;
import com.yheriatovych.reductor.Store;

import javax.inject.Inject;

import butterknife.ButterKnife;
import dagger.android.AndroidInjector;
import timber.log.Timber;

public final class DebugView extends FrameLayout {

    Store<AppState> store;

    private Cancelable mCancelable;

    public DebugView(Context context, Store<AppState> store) {
        this(context, null, store);
    }

    public DebugView(Context context, AttributeSet attrs, Store<AppState> store) {
        super(context, attrs);
        this.store = store;
        // Inflate all of the controls and inject them.
        LayoutInflater.from(context).inflate(R.layout.debug_view_content, this);
        ButterKnife.bind(this);
        DisplayLogicState displayLogicState = store.getState().displayLogic();
        Timber.d("displayLogicState selectId %d", displayLogicState.selectId());
        mCancelable = Cursors.forEach(store, state -> {
            Timber.d("displayLogicState selectId %d", state.displayLogic().selectId());
        });
    }



    public void onDrawerOpened() {
    }
}

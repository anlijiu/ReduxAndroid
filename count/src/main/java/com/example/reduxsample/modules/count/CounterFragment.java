package com.example.reduxsample.modules.count;

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

import com.example.count.R;
import com.example.count.R2;
import com.example.reduxsample.modules.count.base.BaseFragment;
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


public class CounterFragment extends BaseFragment {
    public static final String TAG = CounterFragment.class.getSimpleName();
    private static final int MSG_WHAT_AUTO_INCREASE = 0;
    private static final int MSG_WHAT_AUTO_INCREASE_START = 1;
    private static final int MSG_WHAT_AUTO_INCREASE_STOP = 2;

    @Inject
    Store<CounterState> store;

    @BindView(R2.id.tv_value)
    TextView tvValue;
    @BindView(R2.id.etxt_1)
    EditText etxt1;


    private CounterActions counterActions;
    private Cursor<CounterState> counterCursor;
    private Cancelable counterCancelable;

    private HandlerThread handlerThread;
    private Handler handler;

    public static CounterFragment newInstance() {
        return new CounterFragment();
    }

    @Override
    public int layoutId() {
        return R.layout.fragment_counter;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Timber.e("CounterFragment store is %s", store);
        counterActions = Actions.from(CounterActions.class);
        counterCursor = Cursors.map(store, state -> state);
        counterCancelable = counterCursor.subscribe(state -> tvValue.setText(String.valueOf(state.value())));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        counterCancelable.cancel();
    }

    @OnClick({R2.id.btn_add, R2.id.btn_minus, R2.id.btn_show_router})
    void onClick(View view) {
        Timber.e("CounterFragment onClick view.getId is %d, R2.id.btn_add is %d", view.getId(), R2.id.btn_add);
        if(view.getId() == R.id.btn_add) {
            Timber.e("CounterFragment onClick add, %d", Integer.parseInt(etxt1.getText().toString()));
            store.dispatch(counterActions.add(Integer.parseInt(etxt1.getText().toString())));
        } else if(view.getId() == R.id.btn_minus) {
            store.dispatch(counterActions.minus(Integer.parseInt(etxt1.getText().toString())));
        } else if(view.getId() == R.id.btn_show_router) {
            Routers.open(view.getContext(), "router://home/" + Constants.component_just_show + "/" + Constants.container_right);
        }
    }

    @OnCheckedChanged(R2.id.tbtn_auto_increase)
    void onCheckedChanged (ToggleButton view, boolean isChecked){
        if(isChecked) {
            if(handlerThread == null) {
                initBackgroundThead();
            }
            handler.sendEmptyMessage(MSG_WHAT_AUTO_INCREASE);
        } else {
            handler.removeCallbacksAndMessages(null);
        }
    }

    private void initBackgroundThead() {
        handlerThread = new HandlerThread("background counter auto increase");
        handlerThread.start();
        handler = new Handler(handlerThread.getLooper()) {
            @Override
            public void handleMessage(Message msg)
            {
                switch (msg.what) {
                    case MSG_WHAT_AUTO_INCREASE:
                        store.dispatch(counterActions.add(Integer.parseInt(etxt1.getText().toString())));
                        Message m = this.obtainMessage(MSG_WHAT_AUTO_INCREASE);
                        sendMessageDelayed(m, 1000);
                        break;

                    default:
                        break;
                }
            }
        };
    }
}

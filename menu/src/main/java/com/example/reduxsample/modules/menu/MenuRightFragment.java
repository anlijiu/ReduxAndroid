package com.example.reduxsample.modules.count;

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


import com.example.count.R;
import com.example.count.R2;
import com.example.data.CanBusAgent;
import com.example.reduxsample.modules.count.base.BaseFragment;
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


public class JustReadFragment extends BaseFragment {
    public static final String TAG = JustReadFragment.class.getSimpleName();

    @Inject
    Store<CounterState> store;

    @Inject
    CanBusAgent agent;

    @BindView(R2.id.tv_value)
    TextView tvValue;
    @BindView(R2.id.tv_can_bus_value)
    TextView tvCanBusValue;

    private CounterActions counterActions;
    private Cursor<CounterState> counterCursor;
    private Cancelable counterCancelable;
    private Disposable disposable;

    public static JustReadFragment newInstance() {
        return new JustReadFragment();
    }

    @Override
    public int layoutId() {
        return R.layout.fragment_just_read;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Timber.e("JustReadFragment store is %s", store);
        counterActions = Actions.from(CounterActions.class);
        counterCursor = Cursors.map(store, state -> state);
        counterCancelable = counterCursor.subscribe(state -> {
            tvValue.setText(String.valueOf(state.value()));
            tvCanBusValue.setText(state.canValueForTest());
        });

        disposable = agent.observable().subscribe(value -> store.dispatch(counterActions.updateCanValue(value)));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        disposable.dispose();
        counterCancelable.cancel();
    }
}

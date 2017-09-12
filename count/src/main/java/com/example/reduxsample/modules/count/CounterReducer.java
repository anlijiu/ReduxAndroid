package com.example.reduxsample.modules.count;

import com.yheriatovych.reductor.Reducer;
import com.yheriatovych.reductor.annotations.AutoReducer;

@AutoReducer
public abstract class CounterReducer implements Reducer<CounterState> {

    @AutoReducer.InitialState
    CounterState initState() {
        return CounterState.create(0);
    }

    @AutoReducer.Action(
            value = CounterActions.COUNTER__ADD,
            from = CounterActions.class
    )
    public CounterState counterAdd(CounterState current, int value) {
        return current.toBuilder().value(current.value() + value).build();
    }

    @AutoReducer.Action(
            value = CounterActions.COUNTER__MINUS,
            from = CounterActions.class
    )
    public CounterState counterMinus(CounterState current, int value) {
        return current.toBuilder().value(current.value() - value).build();
    }


    public static CounterReducer create() {
        return new CounterReducerImpl();
    }
}

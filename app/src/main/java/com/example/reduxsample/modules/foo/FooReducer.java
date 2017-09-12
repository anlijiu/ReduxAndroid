package com.example.reduxsample.modules.foo;

import com.yheriatovych.reductor.Reducer;
import com.yheriatovych.reductor.annotations.AutoReducer;

@AutoReducer
public abstract class FooReducer implements Reducer<FooState> {

    @AutoReducer.InitialState
    FooState initState() {
        return FooState.create(0);
    }

    @AutoReducer.Action(
            value = FooActions.SELECT_MAIN_MENU_ITEM,
            from = FooActions.class
    )
    public FooState selectMainMenuItem(FooState current, int id) {
        int selectId = current.selectId() == id ? 0 : id;
        return current.toBuilder().selectId(selectId).build();
    }


    public static FooReducer create() {
        return new FooReducerImpl();
    }
}

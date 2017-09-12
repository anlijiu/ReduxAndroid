package com.example.reduxsample.modules.displayLogic;

import com.yheriatovych.reductor.Reducer;
import com.yheriatovych.reductor.annotations.AutoReducer;

@AutoReducer
public abstract class DisplayLogicReducer implements Reducer<DisplayLogicState> {

    @AutoReducer.InitialState
    DisplayLogicState initState() {
        return DisplayLogicState.create(0, false);
    }

    @AutoReducer.Action(
            value = DisplayLogicActions.SELECT_MAIN_MENU_ITEM,
            from = DisplayLogicActions.class
    )
    public DisplayLogicState selectMainMenuItem(DisplayLogicState current, int id, boolean byUser) {
        int selectId = current.selectId() == id ? 0 : id;
        return current.toBuilder().byUser(byUser).selectId(selectId).build();
    }


    public static DisplayLogicReducer create() {
        return new DisplayLogicReducerImpl();
    }
}

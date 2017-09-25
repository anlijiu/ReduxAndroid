package com.example.reduxsample.modules.menu;

import com.yheriatovych.reductor.Reducer;
import com.yheriatovych.reductor.annotations.AutoReducer;

@AutoReducer
public abstract class MenuReducer implements Reducer<MenuState> {

    @AutoReducer.InitialState
    MenuState initState() {
        return MenuState.create(0, false);
    }

    @AutoReducer.Action(
            value = MenuActions.MENU__SELECT,
            from = MenuActions.class
    )
    public MenuState select(MenuState current, int id, boolean byUser) {
        int selectId = current.selectId() == id ? 0 : id;
        return current.toBuilder().byUser(byUser).selectId(selectId).build();
    }


    public static MenuReducer create() {
        return new MenuReducerImpl();
    }
}

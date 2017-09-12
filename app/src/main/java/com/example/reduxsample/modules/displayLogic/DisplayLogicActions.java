package com.example.reduxsample.modules.displayLogic;

import com.yheriatovych.reductor.Action;
import com.yheriatovych.reductor.annotations.ActionCreator;


@ActionCreator
public interface DisplayLogicActions {
    String SELECT_MAIN_MENU_ITEM = "SELECT_MAIN_MENU_ITEM";


    @ActionCreator.Action(SELECT_MAIN_MENU_ITEM)
    Action selectMainMenuItem(int id, boolean byUser);
}

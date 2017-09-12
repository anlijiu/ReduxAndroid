package com.example.reduxsample.modules.foo;

import com.yheriatovych.reductor.Action;
import com.yheriatovych.reductor.annotations.ActionCreator;

import java.util.List;


@ActionCreator
public interface FooActions {
    String SELECT_MAIN_MENU_ITEM = "SELECT_MAIN_MENU_ITEM";


    @ActionCreator.Action(SELECT_MAIN_MENU_ITEM)
    Action selectMainMenuItem(int id);
}

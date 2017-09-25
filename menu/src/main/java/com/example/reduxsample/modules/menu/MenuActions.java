package com.example.reduxsample.modules.menu;

import com.yheriatovych.reductor.Action;
import com.yheriatovych.reductor.annotations.ActionCreator;

import java.util.List;


@ActionCreator
public interface MenuActions {
    String MENU__SELECT= "MENU__SELECT";

    @ActionCreator.Action(MENU__SELECT)
    Action select(int id, boolean byUser);

}

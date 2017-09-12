package com.example.reduxsample.modules.count;

import com.yheriatovych.reductor.Action;
import com.yheriatovych.reductor.annotations.ActionCreator;

import java.util.List;


@ActionCreator
public interface CounterActions {
    String COUNTER__ADD= "COUNTER__ADD";
    String COUNTER__MINUS= "COUNTER__MINUS";

    @ActionCreator.Action(COUNTER__ADD)
    Action add(int id);

    @ActionCreator.Action(COUNTER__MINUS)
    Action minus(int id);
}

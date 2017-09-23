package com.example.reduxsample.modules.count;

import com.yheriatovych.reductor.Action;
import com.yheriatovych.reductor.annotations.ActionCreator;

import java.util.List;


@ActionCreator
public interface CounterActions {
    String COUNTER__ADD= "COUNTER__ADD";
    String COUNTER__MINUS= "COUNTER__MINUS";
    String COUNTER__CAN_VALUE_FOR_TEST = "COUNTER__CAN_VALUE_FOR_TEST";

    @ActionCreator.Action(COUNTER__ADD)
    Action add(int id);

    @ActionCreator.Action(COUNTER__MINUS)
    Action minus(int id);


    @ActionCreator.Action(COUNTER__CAN_VALUE_FOR_TEST)
    Action updateCanValue(String value);
}

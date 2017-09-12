package com.example.redux.reducers;


import com.yheriatovych.reductor.Action;
import com.yheriatovych.reductor.Reducer;
import com.yheriatovych.reductor.Store;

import java.util.LinkedList;


public class UndoReducer<State> implements Reducer<State> {
    private final Reducer<State> sourceReducer;

    public static Action pop() {
        return Action.create("POP");
    }

    private LinkedList<State> stack = new LinkedList<>();

    public UndoReducer(Reducer<State> sourceReducer) {
        this.sourceReducer = sourceReducer;
    }
    @Override
    public State reduce(State state, Action action) {
        if (action.type.equals("POP")) {
            return stack.isEmpty()
                    ? state
                    : stack.pop();
        } else if (!action.type.equals(Store.INIT_ACTION)) {
            stack.push(state);
        }
        return sourceReducer.reduce(state, action);
    }
}
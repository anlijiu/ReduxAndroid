package com.example.reduxsample.redux.middlewares;


import android.os.Handler;
import android.os.Looper;

import com.yheriatovych.reductor.Dispatcher;
import com.yheriatovych.reductor.Middleware;
import com.yheriatovych.reductor.Store;

public class MainThreadMiddleware<T> implements Middleware<T> {
    private final Handler handler;
    private MainThreadMiddleware() {
        handler = new Handler(Looper.getMainLooper());
    }

    public static <T> MainThreadMiddleware<T> create() {
        return new MainThreadMiddleware<>();
    }

    @Override
    public Dispatcher create(Store<T> store, Dispatcher nextDispatcher) {
        return action -> {
            if (Looper.myLooper() != Looper.getMainLooper()) {
                handler.post(() -> nextDispatcher.dispatch(action));
            } else {
                nextDispatcher.dispatch(action);
            }
        };
    }
}

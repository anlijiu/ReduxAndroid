package com.example.reduxsample.modules.count.di;

import com.example.di.scope.ScreenScope;
import com.example.redux.middlewares.MainThreadMiddleware;
import com.example.redux.reducers.UndoReducer;
import com.example.reduxsample.modules.count.CounterReducer;
import com.example.reduxsample.modules.count.CounterState;
import com.yheriatovych.reductor.Store;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class CounterModule {
    @Singleton
    @Provides
    CounterReducer provideCounterReducer() {
        return CounterReducer.create();
    }

    @Singleton
    @Provides
    Store<CounterState> provideStore(CounterReducer counterReducer) {
        final Store store = Store.create(
                new UndoReducer<>(counterReducer),
                MainThreadMiddleware.create()
        );
        return store;
    }
}

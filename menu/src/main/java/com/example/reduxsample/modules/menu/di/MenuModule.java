package com.example.reduxsample.modules.count.di;

import android.content.Context;

import com.example.di.scope.ScreenScope;
import com.example.plugin.HostDelegate;
import com.example.redux.middlewares.MainThreadMiddleware;
import com.example.redux.reducers.UndoReducer;
import com.example.reduxsample.modules.count.CounterPlugin;
import com.example.reduxsample.modules.count.CounterReducer;
import com.example.reduxsample.modules.count.CounterState;
import com.yheriatovych.reductor.Store;

import dagger.Module;
import dagger.Provides;

@Module
public class CounterModule {

    @Provides
    @ScreenScope
    CounterReducer provideCounterReducer() {
        return CounterReducer.create();
    }

    @Provides
    @ScreenScope
    Store<CounterState> provideStore(CounterReducer counterReducer) {
        final Store store = Store.create(
                new UndoReducer<>(counterReducer),
                MainThreadMiddleware.create()
        );
        return store;
    }

    @Provides
    @ScreenScope
    CounterPlugin providesUserPlugin(@ScreenScope Context context, HostDelegate hostDelegate) {
        return new CounterPlugin(context, hostDelegate);
    }
}

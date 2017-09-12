package com.example.reduxsample.redux;


import com.example.reduxsample.models.AppState;
import com.example.reduxsample.models.AppStateReducer;
import com.example.reduxsample.modules.displayLogic.DisplayLogicReducer;
import com.example.reduxsample.modules.foo.FooReducer;
import com.example.reduxsample.redux.middlewares.MainThreadMiddleware;
import com.example.reduxsample.redux.reducers.UndoReducer;
import com.yheriatovych.reductor.Store;
import com.yheriatovych.reductor.observable.rxjava2.EpicMiddleware;


import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;


@Module
public class ReduxModule {

    @Provides
    @Singleton
    AppStateReducer provideAppStateReducer() {
        return AppStateReducer.builder()
                .fooReducer(FooReducer.create())
                .displayLogicReducer(DisplayLogicReducer.create())
                .build();
    }

    @Provides
    @Singleton
    Store<AppState> provideStore(AppStateReducer appReducer) {
        return Store.create(
                new UndoReducer<>(appReducer),
                MainThreadMiddleware.create()
        );
    }

}

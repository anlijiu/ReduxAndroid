package com.example.reduxsample.modules.user;

import com.example.cloud.service.UserService;
import com.example.di.scope.ScreenScope;
import com.example.redux.middlewares.MainThreadMiddleware;
import com.example.redux.reducers.UndoReducer;
import com.example.reduxsample.di.scope.UserScope;
import com.yheriatovych.reductor.Store;
import com.yheriatovych.reductor.observable.rxjava2.Epic;
import com.yheriatovych.reductor.observable.rxjava2.EpicMiddleware;
import com.yheriatovych.reductor.observable.rxjava2.Epics;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class UserModule {
    @Provides
    @Singleton
    UserEpics provideUserEpics(UserService userService) {
        return new UserEpics(userService);
    }

    @Provides
    @Singleton
    EpicMiddleware<UserState> provideEpicMiddleware(
            UserEpics userEpics
    ) {
        List<Epic<UserState>> epicList = new ArrayList<Epic<UserState>>();
        epicList.addAll(userEpics.epics());
        return EpicMiddleware.create(
                Epics.combineEpics(epicList)
        );
    }

    @Provides
    @Singleton
    UserReducer provideUserReducer() {
        return UserReducer.create();
    }

    @Provides
    @Singleton
    Store<UserState> provideStore(UserReducer appReducer, EpicMiddleware<UserState> epicMiddleware) {
        return Store.create(
                new UndoReducer<>(appReducer),
                MainThreadMiddleware.create(),
                epicMiddleware

        );
    }
}


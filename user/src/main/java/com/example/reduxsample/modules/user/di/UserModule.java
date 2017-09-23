package com.example.reduxsample.modules.user.di;

import android.app.Activity;
import android.content.Context;

import com.example.cloud.service.UserService;
import com.example.di.scope.ScreenScope;
import com.example.plugin.HostDelegate;
import com.example.plugin.PluginConfiguration;
import com.example.redux.middlewares.MainThreadMiddleware;
import com.example.redux.reducers.UndoReducer;
import com.example.reduxsample.modules.user.UserEpics;
import com.example.reduxsample.modules.user.UserPlugin;
import com.example.reduxsample.modules.user.UserReducer;
import com.example.reduxsample.modules.user.UserState;
import com.example.ui.ActivityLifecyclesServer;
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
    @ScreenScope
    UserEpics provideUserEpics(UserService userService) {
        return new UserEpics(userService);
    }

    @Provides
    @ScreenScope
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
    @ScreenScope
    UserReducer provideUserReducer() {
        return UserReducer.create();
    }

    @Provides
    @ScreenScope
    Store<UserState> provideStore(UserReducer appReducer, EpicMiddleware<UserState> epicMiddleware) {
        return Store.create(
                new UndoReducer<>(appReducer),
                MainThreadMiddleware.create(),
                epicMiddleware

        );
    }

    @Provides
    @ScreenScope
    UserPlugin providesUserPlugin(@ScreenScope Context context, HostDelegate hostDelegate) {
        return new UserPlugin(context, hostDelegate);
    }
}


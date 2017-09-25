package com.example.reduxsample.modules.menu.di;

import android.content.Context;

import com.example.di.scope.ScreenScope;
import com.example.plugin.HostDelegate;
import com.example.redux.middlewares.MainThreadMiddleware;
import com.example.redux.reducers.UndoReducer;
import com.example.reduxsample.modules.menu.MenuPlugin;
import com.example.reduxsample.modules.menu.MenuReducer;
import com.example.reduxsample.modules.menu.MenuState;
import com.yheriatovych.reductor.Store;

import dagger.Module;
import dagger.Provides;

@Module
public class MenuModule {

    @Provides
    @ScreenScope
    MenuReducer provideMenuReducer() {
        return MenuReducer.create();
    }

    @Provides
    @ScreenScope
    Store<MenuState> provideStore(MenuReducer menuerReducer) {
        final Store store = Store.create(
                new UndoReducer<>(menuerReducer),
                MainThreadMiddleware.create()
        );
        return store;
    }

    @Provides
    @ScreenScope
    MenuPlugin providesMenuPlugin(@ScreenScope Context context, HostDelegate hostDelegate) {
        return new MenuPlugin(context, hostDelegate);
    }
}

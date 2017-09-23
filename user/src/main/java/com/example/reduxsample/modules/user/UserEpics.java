package com.example.reduxsample.modules.user;

import com.example.cloud.models.UserList;
import com.example.cloud.service.UserService;
import com.example.di.scope.ScreenScope;
import com.yheriatovych.reductor.Action;
import com.yheriatovych.reductor.Actions;
import com.yheriatovych.reductor.Store;
import com.yheriatovych.reductor.observable.rxjava2.Epic;
import com.yheriatovych.reductor.observable.rxjava2.Epics;

import java.util.Arrays;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;


public class UserEpics {
    private UserActions userActions;
    private UserService userService;

    public UserEpics(UserService userService) {
        userActions = Actions.from(UserActions.class);
        this.userService = userService;
    }

    public List<Epic<UserState>> epics() {
        return Arrays.asList(this.searchUserList(), this.loadMore());
    }

    public Epic<UserState> searchUserList()
    {
        return (actions, store) -> {
            return actions.filter(Epics.ofType(UserActions.USER__SEARCH_USERS))
                    .flatMap(action -> userService.searchGithubUsers(action.getValue(0), action.getValue(1)).subscribeOn(Schedulers.io()))
                    .observeOn(AndroidSchedulers.mainThread())
                    .map(userList -> userActions.searchUsersSuccess(userList))
                    .doOnError(throwable -> store.dispatch(Actions.from(UserActions.class).searchUsersError(throwable.getMessage())));
        };

    }

    public Epic<UserState> loadMore() {
        return (actions, store) ->
                actions.filter(Epics.ofType(UserActions.USER__LOAD_MORE))
                        .flatMap(action -> userService.searchGithubUsers(store.getState().searchName(), store.getState().page()+1).subscribeOn(Schedulers.io()))
                        .observeOn(AndroidSchedulers.mainThread())
                        .map(userList -> userActions.loadMoreSuccess(userList))
                        .doOnError(throwable -> store.dispatch(Actions.from(UserActions.class).loadMoreError(throwable.getMessage())));
    }

}

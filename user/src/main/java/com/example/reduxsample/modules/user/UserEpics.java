package com.example.reduxsample.modules.user;

import com.example.cloud.service.UserService;
import com.yheriatovych.reductor.Action;
import com.yheriatovych.reductor.Actions;
import com.yheriatovych.reductor.observable.rxjava2.Epic;
import com.yheriatovych.reductor.observable.rxjava2.Epics;

import java.util.Arrays;
import java.util.List;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public class UserEpics {
    private UserActions userActions;
    private UserService userService;

    public UserEpics(UserService userService) {
        userActions = Actions.from(UserActions.class);
        this.userService = userService;
    }

    public List<Epic<UserState>> epics() {
        return Arrays.asList(this.searchUserList());
    }

    public Epic<UserState> searchUserList()
    {
        return (actions, store) ->
                actions.filter(Epics.ofType(UserActions.USER__SEARCH_USERS))
                    .flatMap(action -> userService.searchGithubUsers(action.getValue(0)).subscribeOn(Schedulers.io()))
                    .observeOn(AndroidSchedulers.mainThread())
                    .map(userList -> userActions.searchUsersSuccess(userList));

    }

}

package com.example.reduxsample.modules.user;


import com.example.cloud.models.User;
import com.example.cloud.models.UserList;
import com.yheriatovych.reductor.Reducer;
import com.yheriatovych.reductor.annotations.AutoReducer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import timber.log.Timber;

@AutoReducer
public abstract class UserReducer implements Reducer<UserState> {

    @AutoReducer.InitialState
    UserState initState() {
        return UserState.create(
                UserList.builder().build(),
                User.builder().build(), "", "");
    }

    @AutoReducer.Action(
            value = UserActions.USER__SEARCH_USERS,
            from = UserActions.class
    )
    public UserState searchUsers(
            UserState current, String q
    ) {
        Timber.i("UserReducer sssss searchUsers q is %s", q);
        return current.toBuilder()
                .userList(UserList.builder().loading(true).build())
                .errorMsg("")
                .searchName(q)
                .build();
    }

    @AutoReducer.Action(
            value = UserActions.USER__SEARCH_USERS_SUCCESS,
            from = UserActions.class
    )
    public UserState searchUsersSuccess(
            UserState current, UserList userList
    ) {
        Timber.i("UserReducer sssss users size is %d", userList.items().size());
        return current.toBuilder()
                .errorMsg("")
                .userList(userList.toBuilder().loading(false).totalCount(userList.items().size()).build())
                .build();
    }

    @AutoReducer.Action(
            value = UserActions.USER__CHOOSE_USER,
            from = UserActions.class
    )
    public UserState chooseUser(
            UserState current, User user
    ) {
        return current.toBuilder()
                .user(user)
                .build();
    }

    public static UserReducer create() {
        return new UserReducerImpl();
    }
}

package com.example.reduxsample.modules.user;

import com.example.cloud.models.User;
import com.example.cloud.models.UserList;
import com.yheriatovych.reductor.Action;
import com.yheriatovych.reductor.annotations.ActionCreator;

import java.util.List;


@ActionCreator
public interface UserActions {
    String USER__SEARCH_USERS = "USER__SEARCH_USERS";
    String USER__SEARCH_USERS_SUCCESS = "USER__SEARCH_USERS_SUCCESS";
    String USER__SEARCH_USERS_ERROR = "USER__SEARCH_USERS_ERROR";

    String USER__GET_USER = "USER__GET_USER";
    String USER__GET_USER_SUCCESS = "USER__GET_USER_SUCCESS";
    String USER__GET_USER_ERROR = "USER__GET_USER_ERROR";

    String USER__CHOOSE_USER = "USER__CHOOSE_USER";

    @ActionCreator.Action(USER__SEARCH_USERS)
    Action searchUsers(String q);
    @ActionCreator.Action(USER__SEARCH_USERS_SUCCESS)
    Action searchUsersSuccess(UserList userList);
    @ActionCreator.Action(USER__SEARCH_USERS_ERROR)
    Action searchUsersError(String errorMsg);

    @ActionCreator.Action(USER__GET_USER)
    Action getUser(String userName);
    @ActionCreator.Action(USER__GET_USER_SUCCESS)
    Action getUserSuccess(User result);
    @ActionCreator.Action(USER__GET_USER_ERROR)
    Action getUserError(String errorMsg);

    @ActionCreator.Action(USER__CHOOSE_USER)
    Action chooseUser(User user);
}

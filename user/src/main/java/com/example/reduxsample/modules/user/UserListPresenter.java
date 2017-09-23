package com.example.reduxsample.modules.user;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.example.cloud.models.User;
import com.example.cloud.models.UserList;
import com.example.di.scope.ScreenScope;
import com.example.reduxsample.modules.user.base.mvpvm.BasePresenter;
import com.yheriatovych.reductor.Actions;
import com.yheriatovych.reductor.Cancelable;
import com.yheriatovych.reductor.Cursor;
import com.yheriatovych.reductor.Cursors;
import com.yheriatovych.reductor.Store;

import javax.inject.Inject;

import timber.log.Timber;

@ScreenScope
public class UserListPresenter extends BasePresenter<UserListFragment, UserListViewModel> {
    Store<UserState> store;
    Cancelable mCancelable;
    UserActions userActions;
    Cursor<UserList> userStateCursor;

    @Inject
    public UserListPresenter(Store<UserState> store) {
        this.store = store;
        userActions = Actions.from(UserActions.class);
        userStateCursor = Cursors.map(store, state -> state.userList());
    }

    public void searchUser(String value) {
        if(!TextUtils.isEmpty(value)) {
            Timber.e("UserListPresenter sssss searchUser %s ", value);
            store.dispatch(userActions.searchUsers(value, 1));
        }
    }

    public void loadMore(Integer totalCount) {
        if(!store.getState().userList().loading()) {
            store.dispatch(userActions.loadMore());
        }
    }

    public void showUserDetail(User user) {
        store.dispatch(userActions.chooseUser(user));
    }

    @Override
    protected void onLoad() {
        super.onLoad();
        mCancelable = userStateCursor.subscribe(state -> {
            Timber.e("UserListPresenter sssss  users size is %d, loading is %b", state.items().size(), state.loading());
            UserListViewModel viewModel = getViewModel();
            viewModel.updateUserList(state.items());
            viewModel.setLoading(state.loading());
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mCancelable.cancel();
    }

    @Override
    protected void onRestore(@NonNull Bundle savedInstanceState) {
        super.onRestore(savedInstanceState);
    }

    @Override
    protected void onSave(@NonNull Bundle outState) {
        super.onSave(outState);
    }
}


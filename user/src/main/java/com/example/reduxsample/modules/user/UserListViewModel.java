package com.example.reduxsample.modules.user;


import android.databinding.BaseObservable;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableInt;
import android.databinding.ObservableList;
import android.support.v7.util.DiffUtil;
import android.util.SparseArray;
import android.view.View;

import com.example.cloud.models.User;
import com.example.di.scope.ScreenScope;
import com.example.reduxsample.modules.user.adapter.UserListAdapter;


import java.util.List;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;
import timber.log.Timber;

@ScreenScope
public class UserListViewModel extends BaseObservable {

    public final ObservableInt loadingVisible = new ObservableInt(View.GONE);
    public final UserListAdapter adapter = new UserListAdapter();
    public final ObservableList<User> items = new ObservableArrayList<>();
//    public final DiffObservableList<User> items =  new DiffObservableList<User>(new DiffObservableList.Callback<User>() {
//        @Override
//        public boolean areItemsTheSame(User oldItem, User newItem) {
//            return oldItem.id() == newItem.id();
//        }
//
//        @Override
//        public boolean areContentsTheSame(User oldItem, User newItem) {
//            return oldItem.login().equals(newItem.login());
//        }
//    });

    @Inject
    public UserListViewModel() {

    }

    public void setLoading(boolean loading) {
        if(loading) {
            loadingVisible.set(View.VISIBLE);
        } else {
            loadingVisible.set(View.GONE);
        }
    }

    public void updateUserList(List<User> users) {
        Timber.d("UserListViewModel  updateUserList  users's size is %d", users.size());
        items.clear();
        items.addAll(users);
//        // On background thread:
//        DiffUtil.DiffResult diffResult = items.calculateDiff(users);
//        // On main thread:
//        items.update(users, diffResult);
    }

    private void addToBottom(List<User> users) {
        items.addAll(users);
    }
}

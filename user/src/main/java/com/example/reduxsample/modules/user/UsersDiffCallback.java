package com.example.reduxsample.modules.user;

import android.support.v7.util.DiffUtil;


import com.example.cloud.models.User;

import java.util.List;

class UsersDiffCallback extends DiffUtil.Callback {
    private final List<User> oldUsers;
    private final List<User> newUsers;

    public UsersDiffCallback(List<User> oldUsers, List<User> newUsers) {
        this.oldUsers = oldUsers;
        this.newUsers = newUsers;
    }

    @Override
    public int getOldListSize() {
        return oldUsers.size();
    }

    @Override
    public int getNewListSize() {
        return newUsers.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldUsers.get(oldItemPosition).id() == newUsers.get(newItemPosition).id();
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return oldUsers.get(oldItemPosition).equals(newUsers.get(newItemPosition));
    }
}

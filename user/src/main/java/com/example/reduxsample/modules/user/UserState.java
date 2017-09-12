package com.example.reduxsample.modules.user;

import android.os.Parcelable;

import com.example.cloud.models.User;
import com.example.cloud.models.UserList;
import com.google.auto.value.AutoValue;

import java.util.List;

import javax.annotation.Nullable;


@AutoValue
public abstract class UserState implements Parcelable {
    @Nullable
    public abstract UserList userList();
    @Nullable
    public abstract User user();

    @Nullable
    public abstract String errorMsg();
    @Nullable
    public abstract String searchName();

    public abstract Builder toBuilder();

    public static UserState create(UserList userList, User user, String errorMsg, String searchName) {
        return builder()
                .userList(userList)
                .user(user)
                .errorMsg(errorMsg)
                .searchName(searchName)
                .build();
    }

    public static Builder builder() {
        return new AutoValue_UserState.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder userList(UserList userList);

        public abstract Builder user(User user);

        public abstract Builder errorMsg(String errorMsg);

        public abstract Builder searchName(String searchName);

        public abstract UserState build();
    }
}
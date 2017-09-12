package com.example.cloud.models;

import android.os.Parcelable;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Nullable;


@AutoValue
public abstract class User implements Parcelable {

    @Nullable
    public abstract Integer id();
    @Nullable
    public abstract String login();
    @Nullable
    public abstract String name();

    @Nullable
    @SerializedName("avatar_url")
    public abstract String avatarUrl();

    public static TypeAdapter<User> typeAdapter(Gson gson) {
        return new AutoValue_User.GsonTypeAdapter(gson);
    }

    public static User create(Integer id, String login, String name, String avatarUrl) {
        return builder()
                .id(id)
                .login(login)
                .name(name)
                .avatarUrl(avatarUrl)
                .build();
    }


    public static Builder builder() {
        return new AutoValue_User.Builder().id(-1).login("").name("").avatarUrl("");
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder name(String name);

        public abstract Builder id(Integer id);

        public abstract Builder login(String login);

        public abstract Builder avatarUrl(String avatarUrl);

        public abstract User build();
    }
}

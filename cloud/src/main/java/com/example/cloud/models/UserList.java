package com.example.cloud.models;

import android.os.Parcelable;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

import java.util.Collections;
import java.util.List;

import javax.annotation.Nullable;

@AutoValue
public abstract class UserList implements Parcelable {


    @Nullable
    @SerializedName("total_count")
    public abstract Integer totalCount();
    @Nullable
    @SerializedName("incomplete_results")
    public abstract Boolean incompleteResults();
    @Nullable
    @SerializedName("items")
    public abstract List<User> items();

    public abstract boolean loading();

    public abstract UserList.Builder toBuilder();

    public static UserList create(Integer totalCount, Boolean incompleteResults, List<User> items, boolean loading) {
        return builder()
                .totalCount(totalCount)
                .incompleteResults(incompleteResults)
                .items(items)
                .loading(loading)
                .build();
    }

    public static TypeAdapter<UserList> typeAdapter(Gson gson) {
        return new AutoValue_UserList.GsonTypeAdapter(gson);
    }

    public static Builder builder() {
        return new AutoValue_UserList.Builder().loading(false).totalCount(0).incompleteResults(false).items(Collections.emptyList());
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder totalCount(Integer totalCount);

        public abstract Builder incompleteResults(Boolean incompleteResults);

        public abstract Builder items(List<User> items);

        public abstract Builder loading(boolean loading);

        public abstract UserList build();
    }
}

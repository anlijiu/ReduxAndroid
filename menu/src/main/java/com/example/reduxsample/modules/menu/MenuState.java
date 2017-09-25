package com.example.reduxsample.modules.menu;

import android.os.Parcelable;

import com.google.auto.value.AutoValue;

import java.util.List;


@AutoValue
public abstract class MenuState implements Parcelable {
    public abstract int selectId();
    public abstract boolean byUser();


    public abstract Builder toBuilder();

    public static MenuState create(int selectId, boolean byUser) {
        return builder()
                .selectId(selectId)
                .byUser(byUser)
                .build();
    }

    public static Builder builder() {
        return new AutoValue_MenuState.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {

        public abstract Builder selectId(int selectId);

        public abstract Builder byUser(boolean byUser);

        public abstract MenuState build();
    }
}

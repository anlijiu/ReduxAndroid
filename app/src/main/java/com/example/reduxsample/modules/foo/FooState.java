package com.example.reduxsample.modules.foo;

import android.os.Parcelable;

import com.google.auto.value.AutoValue;

import java.util.List;


@AutoValue
public abstract class FooState implements Parcelable {
    public abstract int selectId();


    public abstract Builder toBuilder();

    public static FooState create(int selectId) {
        return builder()
                .selectId(selectId)
                .build();
    }

    public static Builder builder() {
        return new AutoValue_FooState.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {

        public abstract Builder selectId(int selectId);

        public abstract FooState build();
    }
}
package com.example.reduxsample.modules.count;

import android.os.Parcelable;

import com.google.auto.value.AutoValue;

import java.util.List;


@AutoValue
public abstract class CounterState implements Parcelable {
    public abstract int value();


    public abstract Builder toBuilder();

    public static CounterState create(int value) {
        return builder()
                .value(value)
                .build();
    }

    public static Builder builder() {
        return new AutoValue_CounterState.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {

        public abstract Builder value(int value);

        public abstract CounterState build();
    }
}

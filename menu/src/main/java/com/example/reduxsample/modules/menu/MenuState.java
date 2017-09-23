package com.example.reduxsample.modules.count;

import android.os.Parcelable;

import com.google.auto.value.AutoValue;

import java.util.List;


@AutoValue
public abstract class CounterState implements Parcelable {
    public abstract int value();
    public abstract String canValueForTest();


    public abstract Builder toBuilder();

    public static CounterState create(int value, String canValueForTest) {
        return builder()
                .value(value)
                .canValueForTest(canValueForTest)
                .build();
    }

    public static Builder builder() {
        return new AutoValue_CounterState.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {

        public abstract Builder value(int value);

        public abstract Builder canValueForTest(String canValueForTest);

        public abstract CounterState build();
    }
}

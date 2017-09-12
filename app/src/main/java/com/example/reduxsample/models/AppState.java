package com.example.reduxsample.models;

/**
 * Created by anlijiu on 17-9-6.
 */

import com.example.reduxsample.modules.displayLogic.DisplayLogicState;
import com.example.reduxsample.modules.foo.FooState;
import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.yheriatovych.reductor.annotations.CombinedState;

@CombinedState
@AutoValue
public abstract class AppState {
    public abstract FooState foo();
    public abstract DisplayLogicState displayLogic();

    public static TypeAdapter<AppState> typeAdapter(Gson gson) {
        return new AutoValue_AppState.GsonTypeAdapter(gson);
    }
}

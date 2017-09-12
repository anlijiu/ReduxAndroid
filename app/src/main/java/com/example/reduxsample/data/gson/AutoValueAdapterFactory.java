package com.example.reduxsample.data.gson;

/**
 * Created by anlijiu on 17-5-11.
 */

import com.google.gson.TypeAdapterFactory;
import com.ryanharter.auto.value.gson.GsonTypeAdapterFactory;

@GsonTypeAdapterFactory
public abstract class AutoValueAdapterFactory implements TypeAdapterFactory {

    public static AutoValueAdapterFactory create() {
        return new AutoValueGson_AutoValueAdapterFactory();
    }
}

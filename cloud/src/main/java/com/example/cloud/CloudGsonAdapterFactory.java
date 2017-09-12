package com.example.cloud;

import com.google.gson.TypeAdapterFactory;
import com.ryanharter.auto.value.gson.GsonTypeAdapterFactory;

@GsonTypeAdapterFactory
public abstract class CloudGsonAdapterFactory implements TypeAdapterFactory {

    public static CloudGsonAdapterFactory create() {
        return new AutoValueGson_CloudGsonAdapterFactory();
    }
}
package com.example.reduxsample.data.gson;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;

public class ExclusionUnderlineStrategy implements ExclusionStrategy {
    @Override
    public boolean shouldSkipField(FieldAttributes fa) {
        return fa.getName().startsWith("_");
    }
    @Override
    public boolean shouldSkipClass(Class<?> clazz) {
        return false;
    }
};


package com.example.reduxsample.di;

import android.view.View;

import static java.lang.annotation.ElementType.METHOD;

import dagger.MapKey;
import dagger.internal.Beta;
import java.lang.annotation.Target;


@Beta
@MapKey
@Target(METHOD)
public @interface ViewKey {
      Class<? extends View> value();
}

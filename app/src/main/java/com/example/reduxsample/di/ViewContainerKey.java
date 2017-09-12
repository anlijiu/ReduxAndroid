package com.example.reduxsample.di;

import com.example.ui.ViewContainer;

import static java.lang.annotation.ElementType.METHOD;


import dagger.MapKey;
import dagger.internal.Beta;
import java.lang.annotation.Target;


@Beta
@MapKey
@Target(METHOD)
public @interface ViewContainerKey {
      Class<? extends ViewContainer> value();
}

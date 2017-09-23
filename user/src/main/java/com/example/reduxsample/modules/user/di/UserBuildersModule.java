package com.example.reduxsample.modules.user.di;

import com.example.di.scope.ScreenScope;
import com.example.reduxsample.modules.user.UserFragment;
import com.example.reduxsample.modules.user.UserListFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;


@Module
public abstract class UserBuildersModule {
    @ContributesAndroidInjector
    abstract UserFragment userFramgentInjector();

    @ContributesAndroidInjector
    abstract UserListFragment userListFramgentInjector();
}

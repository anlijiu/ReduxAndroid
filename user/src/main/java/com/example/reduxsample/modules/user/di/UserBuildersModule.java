package com.example.reduxsample.modules.user.di;

import com.example.di.scope.ScreenScope;
import com.example.reduxsample.di.scope.UserScope;
import com.example.reduxsample.modules.user.UserFragment;
import com.example.reduxsample.modules.user.UserListFragment;
import com.example.reduxsample.modules.user.UserModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;


@Module
public abstract class UserBuildersModule {
//    @UserScope
    @ContributesAndroidInjector
    abstract UserFragment userFramgentInjector();

//    @UserScope
    @ContributesAndroidInjector
    abstract UserListFragment userListFramgentInjector();
}

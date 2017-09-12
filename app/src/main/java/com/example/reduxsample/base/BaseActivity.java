package com.example.reduxsample.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ui.ViewContainer;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import dagger.android.AndroidInjection;
import dagger.android.support.DaggerAppCompatActivity;
/**
 * Created by anlijiu on 17-9-7.
 */

public abstract class BaseActivity extends DaggerAppCompatActivity {

    @Inject
    ViewContainer viewContainer;

    Unbinder unbinder;

    public BaseActivity() {
    }

    protected abstract @LayoutRes int layoutId();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final LayoutInflater layoutInflater = getLayoutInflater();
        ViewGroup container = viewContainer.forActivity(this);
        View view = createView(layoutInflater, layoutId(), container);
        unbinder = ButterKnife.bind(this, view);

    }

    public View createView(LayoutInflater inflater, @LayoutRes int layoutId, ViewGroup parent) {
        return inflater.inflate(layoutId, parent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

}
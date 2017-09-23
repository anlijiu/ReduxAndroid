package com.example.reduxsample.modules.user.base.mvpvm;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.reduxsample.modules.user.base.BaseFragment;

import butterknife.ButterKnife;
import timber.log.Timber;


public abstract class BaseMvpvmFragment<P extends BasePresenter, VM> extends BaseFragment implements IView {
    public static final int VIEW_ID_SELF = 0;
    VM viewModel;
    P presenter;
    IView baseView;
    protected ViewDataBinding binding;

    @Override
    public View createView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
        binding = binding(inflater, container, savedInstanceState);
        return binding.getRoot();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
                if(viewId() != VIEW_ID_SELF) {
            baseView = ButterKnife.findById(view, viewId());
        } else {
            baseView = this;
        }
        viewModel = viewModel();
        presenter = presenter();
        presenter.attach(baseView, viewModel);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.detach(baseView, viewModel);
    }

    protected ViewDataBinding binding(LayoutInflater inflater, ViewGroup container,
                                      Bundle savedInstanceState) {
        ViewDataBinding binding = DataBindingUtil.inflate(inflater, layoutId(), container, false);
        return binding;
    }

    protected @IdRes int viewId() {
        return VIEW_ID_SELF;
    }

    protected abstract <VM> VM viewModel();
    protected abstract P presenter();

}

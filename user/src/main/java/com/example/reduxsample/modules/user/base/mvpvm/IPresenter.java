package com.example.reduxsample.modules.user.base.mvpvm;

import android.support.annotation.NonNull;


public interface IPresenter<V extends IView, VM> {

    /**
     * 注入View，使之能够与View相互响应
     *
     * @param iView
     */
    void attach(@NonNull V iView, VM viewModel);

    /**
     * 释放资源
     */
    void detach(V view, VM viewModel);
}

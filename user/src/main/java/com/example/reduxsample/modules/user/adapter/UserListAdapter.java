package com.example.reduxsample.modules.user.adapter;

import android.databinding.ViewDataBinding;
import android.view.View;

import com.example.cloud.models.User;
import com.example.reduxsample.modules.user.BR;
import com.example.ui.recyclerview.adapter.BaseMvvmRecyclerViewAdapter;

import timber.log.Timber;

/**
 * Created by anlijiu on 17-6-16.
 */

public class UserListAdapter extends BaseMvvmRecyclerViewAdapter<User, ViewDataBinding> {



    @Override
    protected void onBinding(ViewDataBinding binding, User item, int position) {
        super.onBinding(binding, item, position);
        if(onItemClickListener != null) {
            binding.setVariable(BR.onClickListener, onItemClickListener);
        }


    }
}

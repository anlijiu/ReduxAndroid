package com.example.reduxsample.modules.user;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.example.cloud.models.User;
import com.example.reduxsample.modules.user.base.BaseFragment;
import com.facebook.drawee.view.SimpleDraweeView;
import com.yheriatovych.reductor.Actions;
import com.yheriatovych.reductor.Cancelable;
import com.yheriatovych.reductor.Cursor;
import com.yheriatovych.reductor.Cursors;
import com.yheriatovych.reductor.Store;

import javax.inject.Inject;

import butterknife.BindView;

public class UserFragment extends BaseFragment {
    public static final String TAG = UserFragment.class.getSimpleName();
    @Inject
    Store<UserState> store;

    @BindView(R2.id.tv_login)
    TextView tvLogin;
    @BindView(R2.id.tv_id)
    TextView tvID;
    @BindView(R2.id.view_avatar)
    SimpleDraweeView viewAvatar;

    Cancelable mCancelable;
    UserActions userActions;
    Cursor<User> userStateCursor;

    public static UserFragment newInstance() {
        return new UserFragment();
    }

    @Override
    public int layoutId() {
        return R.layout.fragment_user;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        userActions = Actions.from(UserActions.class);
        userStateCursor = Cursors.map(store, state -> state.user());
        mCancelable = userStateCursor.subscribe(state -> {
            User user = state;
            tvID.setText(String.valueOf(user.id()));
            tvLogin.setText(user.login());
            viewAvatar.setImageURI(user.avatarUrl());
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mCancelable.cancel();
    }
}

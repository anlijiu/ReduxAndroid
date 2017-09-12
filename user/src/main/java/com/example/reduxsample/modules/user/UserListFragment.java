package com.example.reduxsample.modules.user;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.cloud.models.UserList;
import com.example.reduxsample.modules.user.base.BaseFragment;
import com.yheriatovych.reductor.Actions;
import com.yheriatovych.reductor.Cancelable;
import com.yheriatovych.reductor.Cursor;
import com.yheriatovych.reductor.Cursors;
import com.yheriatovych.reductor.Store;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dagger.android.support.AndroidSupportInjection;
import timber.log.Timber;


public class UserListFragment extends BaseFragment {
    public static final String TAG = UserListFragment.class.getSimpleName();
    @Inject
    Store<UserState> store;

    Cancelable mCancelable;
    UserActions userActions;
    Cursor<UserList> userStateCursor;

    @BindView(R2.id.btn_search)
    Button btnSearch;
    @BindView(R2.id.etv_search_value)
    EditText etvSearchValue;
    @BindView(R2.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R2.id.tv_loading)
    TextView tvLoading;


    public static UserListFragment newInstance() {
        return new UserListFragment();
    }

    @Override
    public int layoutId() {
        return R.layout.fragment_user_list;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Timber.e("UserListFragment sssss onViewCreated");
        userActions = Actions.from(UserActions.class);

        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        final UserAdapter adapter = new UserAdapter(store.getState().userList().items(),
                user -> store.dispatch(userActions.chooseUser(user)));
        recyclerView.setAdapter(adapter);

        userStateCursor = Cursors.map(store, state -> state.userList());
        mCancelable = userStateCursor.subscribe(state -> {
            Timber.e("UserListFragment sssss  users size is %d, loading is %b", state.items().size(), state.loading());
            adapter.setusers(state.items());
            int loadingVisable = state.loading() ? View.VISIBLE : View.INVISIBLE;
            tvLoading.setVisibility(loadingVisable);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mCancelable.cancel();
    }

    @OnClick(R2.id.btn_search)
    void onClick(View view) {
        String s = etvSearchValue.getText().toString();
        if(!TextUtils.isEmpty(s)) {
            store.dispatch(userActions.searchUsers(s));
        }
    }
}

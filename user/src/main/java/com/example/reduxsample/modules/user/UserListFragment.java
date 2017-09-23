package com.example.reduxsample.modules.user;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.cloud.models.User;

import com.example.reduxsample.modules.user.base.mvpvm.BaseMvpvmFragment;
import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;

import com.example.ui.misc.OnClickListener;
import com.example.ui.misc.command.ReplyCommand;


public class UserListFragment extends BaseMvpvmFragment {
    public static final String TAG = UserListFragment.class.getSimpleName();

    @Inject UserListPresenter presenter;
    @Inject UserListViewModel viewModel;

    @BindView(R2.id.btn_search)
    Button btnSearch;
    @BindView(R2.id.etv_search_value)
    EditText etvSearchValue;
    @BindView(R2.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R2.id.tv_loading)
    TextView tvLoading;

    private final OnClickListener<User> onClickListener = (view, user) -> {
        presenter.showUserDetail(user);
    };

    public final ReplyCommand onLoadMore = new ReplyCommand(new Consumer<Integer>() {
        @Override
        public void accept(Integer totalCount) throws Exception {
            presenter.loadMore(totalCount);
        }
    });

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
    protected ViewDataBinding binding(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewDataBinding binding = super.binding(inflater, container, savedInstanceState);
        binding.setVariable(BR.onItemClickListener, onClickListener);
        binding.setVariable(BR.viewModel, viewModel);
        binding.setVariable(BR.onLoadMore, onLoadMore);
        return binding;
    }

    @Override
    protected UserListPresenter presenter() {
        return presenter;
    }

    @Override
    protected UserListViewModel viewModel() {
        return viewModel;
    }

    @OnClick(R2.id.btn_search)
    void onClick(View view) {
        String s = etvSearchValue.getText().toString();
        presenter.searchUser(s);
    }
}

package com.example.reduxsample.modules.user;

import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.cloud.models.User;

import io.reactivex.functions.Consumer;
import timber.log.Timber;

import java.util.List;

class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {
    private List<User> users;
    private final Consumer<User> onClickListener;
    private int selectPosition = 0;

    public UserAdapter(List<User> users, Consumer<User> onClickListener) {
        this.users = users;
        this.onClickListener = onClickListener;
    }

    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_user, parent, false);

        UserViewHolder viewHolder = new UserViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(UserViewHolder holder, int position) {
        final User user = users.get(position);
        holder.tvID.setText(String.valueOf(user.id()));
        holder.tvLogin.setText(user.login());
        Timber.e("sssss onBindViewHolder  user name is %s, id is %d, login is %s, avatar is %s", user.name(), user.id(), user.login(), user.avatarUrl());
        holder.itemView.setOnClickListener(view -> {
            try {
                onClickListener.accept(user);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (position == RecyclerView.NO_POSITION) return;

            // Updating old as well as new positions
            notifyItemChanged(selectPosition);
            selectPosition = position;
            holder.itemView.setSelected(true);
            notifyItemChanged(selectPosition);

        });
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    @Override
    public long getItemId(int position) {
        return users.get(position).id();
    }

    public void setusers(List<User> users) {

        List<User> oldusers = this.users;
        Timber.e("setusers  users size is %d, old size is %d", users.size(), oldusers.size());
        this.users = users;
        DiffUtil.calculateDiff(new UsersDiffCallback(oldusers, users), false).dispatchUpdatesTo(this);
    }

    static class UserViewHolder extends RecyclerView.ViewHolder {

        public TextView tvID;
        public TextView tvLogin;
        public UserViewHolder(View itemView) {
            super(itemView);
            tvID = (TextView) itemView.findViewById(R.id.tv_id);
            tvLogin = (TextView) itemView.findViewById(R.id.tv_login);
        }
    }

}

package com.example.ui.bindingadapter.recyclerview;

import android.databinding.BindingAdapter;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.widget.RadioButton;


import com.example.ui.misc.OnClickListener;
import com.example.ui.misc.command.ReplyCommand;
import com.example.ui.recyclerview.LayoutManagers;
import com.example.ui.recyclerview.adapter.BaseMvvmRecyclerViewAdapter;
import com.example.ui.recyclerview.adapter.RecyclerViewAdapterFactory;
import com.example.ui.recyclerview.adapterdelegates.AdapterDelegate;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.subjects.PublishSubject;
import timber.log.Timber;


public class ViewBindingAdapter {

    @SuppressWarnings("unchecked")
    @BindingAdapter(value = {"itemLayout", "delegates", "items", "adapter", "onItemClickListener", "viewModel"}, requireAll = false)
    public static  <T, VM>void setDelegates(RecyclerView recyclerView, @LayoutRes int layoutId, SparseArray<AdapterDelegate> delegates, List<T> items,
                                            BaseMvvmRecyclerViewAdapter adapter, OnClickListener listener, VM viewModel) {
        if (layoutId == 0 && delegates == null) {
            throw new IllegalArgumentException("DataBindingAdapter: BaseMvvmAdapter delegates must not be null");
        }
        RecyclerViewAdapterFactory factory = RecyclerViewAdapterFactory.DEFAULT;
//        if (factory == null) {
//            factory = RecyclerViewAdapterFactory.DEFAULT;
//        }
        BaseMvvmRecyclerViewAdapter oldAdapter = (BaseMvvmRecyclerViewAdapter) recyclerView.getAdapter();
        if (adapter == null) {
            if (oldAdapter == null) {
                adapter = factory.create(recyclerView, items);
            } else {
                adapter = oldAdapter;
            }
        }
        adapter.setItems(items);
        if(layoutId != 0) {
            adapter.setItemLayout(layoutId);
        } else {
            adapter.setDelegates(delegates);
        }

        adapter.setOnItemClickListener(listener);
        adapter.setViewModel(viewModel);
        if (oldAdapter != adapter) {
            recyclerView.setAdapter(adapter);
        }
    }

    @BindingAdapter("layoutManager")
    public static void setLayoutManager(RecyclerView recyclerView, LayoutManagers.LayoutManagerFactory layoutManagerFactory) {
        recyclerView.setLayoutManager(layoutManagerFactory.create(recyclerView));
    }


    @BindingAdapter(value = {"onLoadMoreCommand"}, requireAll = false)
    public static void onLoadMoreCommand(RecyclerView recyclerView, ReplyCommand onLoadMoreCommand) {
        RecyclerView.OnScrollListener listener = new OnScrollListener(onLoadMoreCommand);
        recyclerView.addOnScrollListener(listener);
    }

    @BindingAdapter(value = {"onScrollChangeCommand", "onScrollStateChangedCommand"}, requireAll = false)
    public static void onScrollChangeCommand(final RecyclerView recyclerView,
                                             final ReplyCommand<ScrollDataWrapper> onScrollChangeCommand,
                                             final ReplyCommand<Integer> onScrollStateChangedCommand) {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            private int state;

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (onScrollChangeCommand != null) {
                    onScrollChangeCommand.execute(new ScrollDataWrapper(dx, dy, state));
                }
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                state = newState;
                if (onScrollStateChangedCommand != null) {
                    onScrollChangeCommand.equals(newState);
                }
            }
        });

    }

    @BindingAdapter("selection")
    public static void setSelectTime(RadioButton radioButton,boolean b){
        if(b){
            radioButton.setChecked(true);
        }else {
            radioButton.setChecked(false);
        }
    }

    public static class OnScrollListener extends RecyclerView.OnScrollListener {

        private PublishSubject<Integer> methodInvoke = PublishSubject.create();

        private ReplyCommand<Integer> onLoadMoreCommand;

        public OnScrollListener(ReplyCommand<Integer> onLoadMoreCommand) {
            this.onLoadMoreCommand = onLoadMoreCommand;
            methodInvoke.throttleFirst(1, TimeUnit.SECONDS)
                    .subscribe(c -> onLoadMoreCommand.execute(c));
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
            int visibleItemCount = layoutManager.getChildCount();
            int totalItemCount = layoutManager.getItemCount();
            int pastVisiblesItems = layoutManager.findFirstVisibleItemPosition();
            if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                if (onLoadMoreCommand != null) {
                    methodInvoke.onNext(recyclerView.getAdapter().getItemCount());
                }
            }
        }

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }


    }

    public static class ScrollDataWrapper {
        public float scrollX;
        public float scrollY;
        public int state;

        public ScrollDataWrapper(float scrollX, float scrollY, int state) {
            this.scrollX = scrollX;
            this.scrollY = scrollY;
            this.state = state;
        }
    }


}

package com.example.ui.recyclerview.adapter;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ui.CustomRecyclerView;
import com.example.ui.misc.OnClickListener;
import com.example.ui.recyclerview.LayoutManagers;
import com.example.ui.recyclerview.adapterdelegates.AdapterDelegate;
import com.example.ui.recyclerview.adapterdelegates.AdapterDelegatesManager;

import java.util.Collections;
import java.util.List;
import java.util.logging.LogRecord;

import io.reactivex.functions.Consumer;
import timber.log.Timber;
import com.example.ui.BR;

import static com.example.utils.ViewUtils._hasFocused;


public class BaseMvvmRecyclerViewAdapter<T, VB extends ViewDataBinding> extends RecyclerView.Adapter<BaseMvvmRecyclerViewAdapter.ViewHolder<VB>> {
    private static final int MSG_HAS_FOCUS = 0;
    private static final int MSG_LOSE_FOCUS = 1;

    protected AdapterDelegatesManager<List<T>> delegatesManager = new AdapterDelegatesManager<>();
    protected List<T> items;
    protected OnClickListener onItemClickListener;
    protected T viewModel;
    protected int itemLayout;
    private int selectPosition = 0;
    private View selectedView;
    private boolean parentHasFocus = false;

    public void focusOnSelectedView() {
        Timber.d("focusOnSelectedView selectedView is %s", selectedView);
        if(selectedView != null) {
            selectedView.requestFocus();
        }
    }

    public int getSelectPosition() {
        return selectPosition;
    }

    public BaseMvvmRecyclerViewAdapter() {
        this(Collections.<T>emptyList());
    }

    public BaseMvvmRecyclerViewAdapter(@NonNull List<T> items) {
        initDelegates();
        setItems(items);
    }

    public List<T> getItems() {
        return items;
    }

    public void setViewModel(T iViewModel){
        this.viewModel = iViewModel;
    }

    public void setItems(@NonNull List<T> items) {
        if(items != null) {
            this.items = items;
        } else {
            this.items = Collections.emptyList();
        }
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnClickListener listener) { this.onItemClickListener = listener; }

    public void setItemLayout(@LayoutRes int itemLayout) {
        this.itemLayout = itemLayout;
    }

    public void setDelegates(SparseArray<AdapterDelegate> delegates) {
        Timber.d(" BaseMvvmRecyclerViewAdapter delegates size is %d", delegates.size());
        for(int index = 0; index < delegates.size(); index++) {
            delegatesManager.addDelegate(delegates.keyAt(index), true, delegates.valueAt(index));
        }
    }

    protected void initDelegates() {
    }

    public static class ViewHolder<VB extends ViewDataBinding> extends RecyclerView.ViewHolder {
        private VB v;

        public ViewHolder(VB v) {
            super(v.getRoot());
            this.v = v;
        }

        public VB getBinding() {
            return v;
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
    }

    @Override
    public int getItemViewType(int position) {
        if(itemLayout != 0) return itemLayout;
        return delegatesManager.getItemViewType(items, position);
    }

    @Override
    public BaseMvvmRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewDataBinding bind = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), viewType, parent, false);
        bind.getRoot().setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(!_hasFocused(parent)) {
                    if(parent instanceof CustomRecyclerView) {
                        ((CustomRecyclerView) parent).setFirstInToTrue();
                    }
                }
            }
        });
        return new ViewHolder<>(bind);
    }

    @Override
    public void onBindViewHolder(BaseMvvmRecyclerViewAdapter.ViewHolder<VB> holder, int position) {
        final T item = items.get(position);
        ViewDataBinding binding = holder.getBinding();
        onBinding(binding, item, position);
    }

    protected  void onBinding(ViewDataBinding binding, T item, int position) {
        binding.setVariable(BR.item, item);

        binding.getRoot().setSelected(selectPosition == position);
        binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Timber.d("selectPosition %d", selectPosition);
                notifyItemChanged(selectPosition);
                selectPosition = position;
                view.setSelected(true);
                selectedView = view;
                notifyItemChanged(selectPosition);
                onItemClickListener.onClick(view, item);
            }
        });
    };

    @Override
    public int getItemCount() {
        return items.size();
    }

}

package com.example.ui;

import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;

import com.example.ui.recyclerview.adapter.BaseMvvmRecyclerViewAdapter;

import timber.log.Timber;

/**
 * Created by anlijiu on 17-9-18.
 */

public class CustomRecyclerView extends RecyclerView {

    boolean firstFocusIn = true;

    public CustomRecyclerView(Context context) {
        super(context, null, 0);
    }
    public CustomRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs, 0);
    }
    public CustomRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected boolean onRequestFocusInDescendants(int direction, Rect previouslyFocusedRect) {
        return super.onRequestFocusInDescendants(direction, previouslyFocusedRect);
    }

    @Override
    public void requestChildFocus(View child, View focused) {
        super.requestChildFocus(child, focused);
        focusOnSelectedItem();
    }

    @Override
    public boolean dispatchUnhandledMove(View focused, int direction) {
        return super.dispatchUnhandledMove(focused, direction);
    }

    @Override
    public void dispatchWindowFocusChanged(boolean hasFocus) {
        super.dispatchWindowFocusChanged(hasFocus);
    }

    public void setFirstInToTrue() {
        firstFocusIn = true;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        Timber.d("onLayout");
    }

    private void focusOnSelectedItem() {
        if(firstFocusIn) {
            firstFocusIn = false;
            Adapter adapter = getAdapter();
            if(adapter instanceof BaseMvvmRecyclerViewAdapter) {
                int selectedPosition = ((BaseMvvmRecyclerViewAdapter) adapter).getSelectPosition();
                scrollToPosition(selectedPosition);

                post(() -> {
                    ViewHolder viewHolder = findViewHolderForAdapterPosition(selectedPosition);
                    if(viewHolder != null && viewHolder.itemView !=null) {
                        viewHolder.itemView.requestFocus();
                    }
                });

            }
        }
    }

}

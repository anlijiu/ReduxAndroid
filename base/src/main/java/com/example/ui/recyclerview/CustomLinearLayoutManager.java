package com.example.ui.recyclerview;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.util.AttributeSet;
import android.view.View;

import timber.log.Timber;

/**
 * Created by anlijiu on 17-9-19.
 */

public class CustomLinearLayoutManager extends LinearLayoutManager {
    public CustomLinearLayoutManager(Context context) {
        super(context, null, 0, 0);
    }
    public CustomLinearLayoutManager(Context context, AttributeSet attrs) {
        super(context, attrs, 0, 0);
    }
    public CustomLinearLayoutManager(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr, 0);
    }
    public CustomLinearLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public CustomLinearLayoutManager(Context context, int orientation, boolean reverseLayouts) {
        super(context, orientation, reverseLayouts);
    }


    @Override
    public View onInterceptFocusSearch(View focused, int direction) {
        Timber.d(" onInterceptFocusSearch focusedView is %s, direction is %d", focused, direction);
        return super.onInterceptFocusSearch(focused, direction);
    }
}

package com.example.ui;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.utils.ViewUtils;

import timber.log.Timber;

/**
 * Created by anlijiu on 17-9-19.
 */

public class CustomLinearLayout extends LinearLayout implements OnChildLoseFocusListener {
    private boolean firstIn = true;

    public CustomLinearLayout(Context context) {
        super(context);
    }

    public CustomLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public CustomLinearLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        Timber.d("onFinishInflate, child count is %d", getChildCount());
    }

    @Override
    public void requestChildFocus(View child, View focused) {
        super.requestChildFocus(child, focused);
        if(firstIn) {
            getChildAt(0).requestFocus();
        }
    }

    @Override
    public void onChildLoseFocus(View view) {
        if(!ViewUtils._hasFocused(CustomLinearLayout.this)) {
            Timber.d("xxxxx  onFocusChange 111");
            firstIn = true;
        } else {
            firstIn = false;
        }
    }
}

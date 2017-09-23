package com.example.ui;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;

import com.example.utils.ViewUtils;

import timber.log.Timber;

/**
 * Created by anlijiu on 17-9-19.
 */

public class CustomItemLinearLayout extends LinearLayout {
    FocusChangeDelegate delegate;
    public CustomItemLinearLayout(Context context) {
        this(context, null);
    }

    public CustomItemLinearLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomItemLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public CustomItemLinearLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        delegate = new FocusChangeDelegate(this);
    }

    @Override
    public boolean onKeyPreIme(int keyCode, KeyEvent event) {
        Timber.d("CustomItemLinearLayout onKeyPreIme");
        if(keyCode == KeyEvent.KEYCODE_ENTER) {
            setDescendantFocusability(FOCUS_BEFORE_DESCENDANTS);
            Timber.d("CustomLinearLayout key enter");
            getChildAt(0).requestFocus();
            getChildAt(0).setOnFocusChangeListener(new OnFocusChangeListener() {
                @Override
                public void onFocusChange(View view, boolean b) {
                    if(!b) {
                        CustomItemLinearLayout.this.setDescendantFocusability(FOCUS_BLOCK_DESCENDANTS);
                        delegate.onChildLoseFocus(view);
                    }
                }
            });
        }
        return super.onKeyPreIme(keyCode, event);
    }
}

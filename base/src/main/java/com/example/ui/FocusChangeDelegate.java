package com.example.ui;

import android.view.View;
import android.view.ViewTreeObserver;

public class FocusChangeDelegate implements OnChildLoseFocusListener {
    View presentView;
    boolean firstFocusIn = true;

    public FocusChangeDelegate(View view) {
        presentView = view;
        view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                view.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View view, boolean b) {
                        if(!b) {
                            onChildLoseFocus(view);
                        }
                    }
                });
            }
        });
    }

    @Override
    public void onChildLoseFocus(View view) {
        View parent = (View) presentView.getParent();
        if(parent instanceof OnChildLoseFocusListener) {
            ((OnChildLoseFocusListener) parent).onChildLoseFocus(view);
        }
    }
}

package com.example.utils;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import timber.log.Timber;

/**
 *
 */
public class ViewUtils {
    /**
     * @param parent
     * @return
     * 当parent或者parent的直接子节点拥有focus返回true
     * 否则返回false
     */
    public static boolean _hasDirectChildFocused(View parent) {
        if(parent.isFocused()) return true;
        if(parent instanceof ViewGroup) {
            Timber.d("xxxx children count is %d", ((ViewGroup) parent).getChildCount());
            for (int i = 0; i < ((ViewGroup)parent).getChildCount(); i++) {
                if (_hasFocused(((ViewGroup)parent).getChildAt(i))) return true;
            }
        }
        return false;
    }

    /**
     * @param parent
     * @return
     * 递归查询parent所有子孙节点，任意节点拥有focus返回true
     * 否则返回false
     */
    public static boolean _hasFocused(View parent) {
        if(parent.isFocused()) return true;
        if(parent instanceof ViewGroup) {
            Timber.d("xxxx children count is %d", ((ViewGroup) parent).getChildCount());
            for (int i = 0; i < ((ViewGroup)parent).getChildCount(); i++) {
                if (_hasFocused(((ViewGroup)parent).getChildAt(i))) return true;
            }
        }
        return false;
    }


    /**
     * 将dip 转为 px
     * @param context
     * @param dipValue
     * @return
     */
    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    /**
     * 将px 转为 dip
     * @param context
     * @param pxValue
     * @return
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

}

package com.example.ui.misc;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ui.R;
import com.example.utils.ViewUtils;

/**
 * Created by anlijiu on 17-6-26.
 */

public class FlexibleToast {

    public static final int GRAVITY_BOTTOM = 0;
    public static final int GRAVITY_CENTER = 1;
    public static final int GRAVITY_TOP = 2;
    public static final int TOAST_SHORT = 0;
    public static final int TOAST_LONG = 1;

    private Context mContext;

    private Toast flexibleToast;

    public void toastShow(Builder builder) {
        if (flexibleToast == null) {
            flexibleToast = new Toast(mContext);
        }
        // toast position
        if (builder.mGravity == GRAVITY_CENTER) {
            flexibleToast.setGravity(Gravity.CENTER | Gravity.CENTER_VERTICAL, 0, 0);
        } else if (builder.mGravity == GRAVITY_TOP) {
            flexibleToast.setGravity(Gravity.TOP | Gravity.CENTER_VERTICAL, 0, ViewUtils.dip2px(mContext, 20));
        } else {
            flexibleToast.setGravity(Gravity.BOTTOM | Gravity.CENTER_VERTICAL, 0, ViewUtils.dip2px(mContext, 20));
        }
        if (builder.mDuration == TOAST_LONG) {
            flexibleToast.setDuration(Toast.LENGTH_LONG);
        } else {
            flexibleToast.setDuration(Toast.LENGTH_SHORT);
        }
        if (builder.hasCustomerView && builder.mCustomerView != null) {
            flexibleToast.setView(builder.mCustomerView);
        } else {
            flexibleToast.setView(builder.mDefaultView);
        }
        flexibleToast.show();
    }

    public FlexibleToast(Context context) {
        mContext = context;
    }

    /**
     * 控制Toast的显示样式
     */
    public static class Builder {
        private View mDefaultView;
        private View mCustomerView;
        private ImageView mIvImage;
        private TextView mTvFirst;
        private TextView mTvSecond;

        private View dividerFirst;
        private View dividerSecond;

        private int mDuration = Toast.LENGTH_SHORT;// 0 short, 1 long
        private int mGravity = 0;
        private boolean hasCustomerView = false; // 是否使用自定义layout


        /**
         * 使用全局的ApplicationContext进行初始化
         * @param context
         */
        public Builder(Context context) {
            mDefaultView = LayoutInflater.from(context).inflate(R.layout.layout_toast_flexible, null);
            mIvImage = (ImageView) mDefaultView.findViewById(R.id.iv_img);
            mTvFirst = (TextView) mDefaultView.findViewById(R.id.tv_text_first);
            mTvSecond = (TextView) mDefaultView.findViewById(R.id.tv_text_second);
            dividerFirst = mDefaultView.findViewById(R.id.divider_first);
            dividerSecond = mDefaultView.findViewById(R.id.divider_second);
        }

        public Builder setImageResource(int resId) {
            this.mIvImage.setImageResource(resId);
            this.mIvImage.setVisibility(View.VISIBLE);
            this.dividerFirst.setVisibility(View.VISIBLE);
            return this;
        }

        public Builder setFirstText(String firstText) {
            this.mTvFirst.setText(firstText);
            this.mTvFirst.setVisibility(View.VISIBLE);
            this.dividerSecond.setVisibility(View.VISIBLE);
            return this;
        }

        public Builder setSecondText(String secondText) {
            this.mTvSecond.setText(secondText);
            this.mTvSecond.setVisibility(View.VISIBLE);
            return this;
        }

        public Builder setDuration(int duration) {
            this.mDuration = duration;
            return this;
        }

        public Builder setGravity(int gravity) {
            this.mGravity = gravity;
            return this;
        }

        /**
         * 为Toast指定自定义的layout，此时上面对ImageView和TextView的设置失效。
         * @param customerView
         * @return
         */
        public Builder setCustomerView(View customerView) {
            this.mCustomerView = customerView;
            this.hasCustomerView = true;
            return this;
        }
    }

}
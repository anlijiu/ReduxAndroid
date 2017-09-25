package com.example.plugin;

import android.app.Dialog;
import android.support.annotation.IntDef;
import android.support.v4.app.Fragment;
import android.view.View;

import com.example.__host.A01HostDelegate;
import com.example.statemachine.StateMachine;
import com.example.ui.misc.FlexibleToast;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;

/**
 * Created by anlijiu on 17-9-23.
 */

public abstract class HostDelegate {
    private ArrayList<OnSelectMenuChangeListener> mOnSelectMenuChangeListeners;
    protected @Menu int selectMenuItem = Menu.NONE;

    public void show(View view, @Position int position) {
        layout().show(view, position);
    }

    public void show(Fragment fragment, @Position int position) {
        layout().show(fragment, position);
    }

    public void popup(Dialog dialog) {
        layout().popup(dialog);
    }

    public void showToastByBuilder(FlexibleToast.Builder builder) {
        layout().showToastByBuilder(builder);
    }

    public interface OnSelectMenuChangeListener {
        void onSelectMenuChanged(@Menu int item);
    }

    public void addOnMenuClickListener(OnSelectMenuChangeListener listener) {
        if (mOnSelectMenuChangeListeners == null) {
            mOnSelectMenuChangeListeners = new ArrayList<OnSelectMenuChangeListener>();
        }
        if (!mOnSelectMenuChangeListeners.contains(listener)) {
            mOnSelectMenuChangeListeners.add(listener);
        }
    }
    public void removeOnMenuClickListener(OnSelectMenuChangeListener listener) {
        if (mOnSelectMenuChangeListeners == null) {
            return;
        }
        mOnSelectMenuChangeListeners.remove(listener);
    }

    public void selectMenu(@Menu int menuItem) {
        this.selectMenuItem = menuItem;
        if (mOnSelectMenuChangeListeners != null) {
            ArrayList<OnSelectMenuChangeListener> listenersCopy =
                    (ArrayList<OnSelectMenuChangeListener>)mOnSelectMenuChangeListeners.clone();
            int numListeners = listenersCopy.size();
            for (int i = 0; i < numListeners; ++i) {
                listenersCopy.get(i).onSelectMenuChanged(menuItem);
            }
        }
    }

    public @Menu int selectMenu() {
        return this.selectMenuItem;
    }

    public abstract Layout layout();

    public interface Layout {
        void show(View view, @Position int position);
        void show(Fragment fragment, @Position int position);
        void showToastByBuilder(FlexibleToast.Builder builder);
//        void hide(View view, @Position int position);
//        void hide(Fragment fragment, @Position int position);

        void popup(Dialog dialog);
    }

    public abstract StateMachine<PowerModeState, PowerModeEvent> powerModeStateMachine();

    // normal, wuliu, zhanche, tuoche
//    int mode();
//
//    // R N D P
//    int gear();
//    //
//    int speed();

    /**
     * 主界面menu类型enum
     */
    @IntDef({Menu.NONE, Menu.CLIMATE, Menu.LIGHT,
            Menu.PHONE, Menu.MAP, Menu.SETTING})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Menu {
        int NONE = 0;
        int CLIMATE = 1;
        int LIGHT = 2;
        int PHONE = 3;
        int MAP = 4;
        int SETTING = 5;
    }

    /**
     * 模块显示位置enum
     */
    @IntDef({Position.TOP_BAR, Position.LEFT_MENU, Position.B13_AREA,
            Position.METER_AREA, Position.APP_AREA, Position.APP_SHORTCUT_AREA,
            Position.RIGHT_MENU})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Position {
        int TOP_BAR = 0;
        int LEFT_MENU = 1;
        int B13_AREA = 2;
        int METER_AREA = 3;
        int APP_AREA = 4;
        int APP_SHORTCUT_AREA = 5;
        int RIGHT_MENU = 6;
    }

    /**
     * powermode状态机enum
     */
    public enum PowerModeState {
        SLEEP,
        OFF,
        ACC,
        ON
    }

    /**
     * powermode状态机事件enum
     */
    public enum PowerModeEvent {
        SLEEP,
        OFF,
        ACC,
        ON
    }

}

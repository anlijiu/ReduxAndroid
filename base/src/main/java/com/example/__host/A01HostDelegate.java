package com.example.__host;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.View;

import io.reactivex.Flowable;

/**
 * Created by anlijiu on 17-9-14.
 */

public interface A01HostDelegate {


    Flowable<String> bus();
    Layout layout();
    A01StateMachine machine();
    Context context();

    interface Layout {

        enum POSITION {
            INDICATOR_AREA, MENU_AREA, B13_AREA, METER_AREA, APP_AREA, APP_SHORTCUT_AREA;
        }

        void show(View view, POSITION position);
        void show(Fragment fragment, POSITION position);

        void hide(View view, POSITION position);
        void hide(Fragment fragment, POSITION position);

        void popup(Dialog dialog);
    }


    interface A01StateMachine {
        // SLEEP, OFF, ACC, ON
        int powermode();

        // normal, wuliu, zhanche, tuoche
        int mode();

        // R N D P
        int gear();

        //
        int speed();


    }
}

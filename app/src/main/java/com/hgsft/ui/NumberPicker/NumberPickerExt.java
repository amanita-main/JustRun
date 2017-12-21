package com.hgsft.ui.NumberPicker;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;

import me.angrybyte.numberpicker.view.ActualNumberPicker;

/**
 * Created by Ilya on 16.12.2017.
 */

/**
 * Extension for library control ActualNumberPicker.
 * Supports TouchEvent listener
 * May be replaced with other component in future
 */
public class NumberPickerExt extends ActualNumberPicker {

    private Handler mEventsHandler = new Handler();
    private ITouchEventListnener mTouchListener;

    public NumberPickerExt(Context context) {
        super(context);
    }

    public NumberPickerExt(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NumberPickerExt(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public NumberPickerExt(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void setOnTouchEventListnener(ITouchEventListnener listener) {
        mTouchListener = listener;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        final MotionEvent action = event;
        mEventsHandler.post(new Runnable() {
            @Override
            public void run() {
                if (mTouchListener != null) {
                    mTouchListener.OnTouchEvent(action);
                }
            }
        });
        return super.onTouchEvent(event);
    }
}

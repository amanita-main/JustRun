package com.hgsft.ui.NumberPicker;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import me.angrybyte.numberpicker.view.ActualNumberPicker;

/**
 * Created by Ilya on 20.12.2017.
 */

/**
 * Extends NumberPickerExt to have a more predictable value changes on scrolling
 * May be replaced with other component in future
 */
public class AutoAdjustableNumberPicker extends NumberPickerExt {

    private float prevPercent;
    private float currentPercent;
    private int currentValue;

    private ITouchEventListnener listener = new ITouchEventListnener() {
        @Override
        public void OnTouchEvent(MotionEvent motionEvent) {
            int eventAction = motionEvent.getAction();
            currentPercent = motionEvent.getX() /getWidth();
            switch (eventAction) {
                case MotionEvent.ACTION_DOWN:
                    //save pecent on touch
                    prevPercent = currentPercent;
                    break;
                case MotionEvent.ACTION_UP:
                    //update current value on touch up, and reset percent values
                    currentValue += (int)((getMaxValue() - getMinValue()) * (currentPercent - prevPercent));
                    currentPercent = 0;
                    prevPercent = 0;
                    break;
            }
        }
    };

    public AutoAdjustableNumberPicker(Context context) {
        super(context);
        init();
    }

    public AutoAdjustableNumberPicker(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public AutoAdjustableNumberPicker(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setOnTouchEventListnener(listener);
        prevPercent = 0;
        currentPercent = 0;
        currentValue = getValue();
    }

    public int getCurrentValue() {
        return currentValue + (int)((getMaxValue() - getMinValue()) * (currentPercent - prevPercent));
    }
    public void setCurrentValue(int value) {
        currentValue = value;
    }

}

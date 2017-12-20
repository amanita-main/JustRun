package com.hgsft.ui.NumberPicker;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import me.angrybyte.numberpicker.view.ActualNumberPicker;

/**
 * Created by Ilya on 20.12.2017.
 */

public class AutoAdjustableNumberPicker extends NumberPickerExt {

    private ITouchEventListnener listener = new ITouchEventListnener() {
        @Override
        public void OnTouchEvent(int eventAction) {
            if (eventAction == MotionEvent.ACTION_UP) {
                int middleValue = getMaxValue() - getMinValue();
                int value = getValue();
                if (value >= middleValue) {
                    setMinValue(value - middleValue);
                    setMaxValue(value + middleValue);
                }
            }
        }
    };

    public AutoAdjustableNumberPicker(Context context) {
        super(context);
    }

    public AutoAdjustableNumberPicker(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AutoAdjustableNumberPicker(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

}

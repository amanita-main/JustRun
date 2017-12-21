package com.hgsft.justrun;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.hgsft.justrun.model.tasks.RunTaskManager;
import com.hgsft.ui.NumberPicker.AutoAdjustableNumberPicker;

import java.util.HashMap;
import java.util.Map;

import me.angrybyte.numberpicker.listener.OnValueChangeListener;

/**
 * Created by Ilya on 20.12.2017.
 */

public class RunModeSettingsDialog {
    private static final Map<RunTaskManager.Mode, int[]> modeDialogSettings = new HashMap<RunTaskManager.Mode, int[]>() {{
        //Resource id as key, value is:
        //                             0 - layout id, 1 - title text id, 2 - result suffix value, 3 - result getter(s)?
        put(RunTaskManager.Mode.DISTANCE, new int[] {R.layout.dialog_number_picker, R.string.distance, R.string.distanceSuffixKM, R.id.actual_picker});
        put(RunTaskManager.Mode.SPEED, new int[] {R.layout.dialog_number_picker, R.string.distance, R.string.distanceSuffixKM, R.id.actual_picker});
        put(RunTaskManager.Mode.TIME, new int[] {R.layout.dialog_number_picker, R.string.distance, R.string.distanceSuffixKM, R.id.actual_picker});
        put(RunTaskManager.Mode.ADVANCED, new int[] {R.layout.dialog_number_picker, R.string.distance, R.string.distanceSuffixKM, R.id.actual_picker});
        //TODO: put right args here
    }};
    public static void openDialog(final Activity ctx, final Button btn, RunTaskManager.Mode mode) {
        //TODO: may be make this shitty code better?
        final AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
        final int[] settingsKey = modeDialogSettings.get(mode);
        int layoutId = settingsKey[0];
        final View view = LayoutInflater.from(ctx).inflate(layoutId, null);
        final AutoAdjustableNumberPicker valuePicker = (AutoAdjustableNumberPicker)view.findViewById(settingsKey[3]);
        final String suffix = ctx.getResources().getString(settingsKey[2]);
        final TextView textView = (TextView)view.findViewById(R.id.textView);

        valuePicker.setListener(new OnValueChangeListener() {
            @Override
            public void onValueChanged(int oldValue, int newValue) {
                float value = ((float)valuePicker.getCurrentValue()) / 10;
                if (value < 0) {
                    value = 0;
                    valuePicker.setCurrentValue(0);
                }
                textView.setText(String.format("%.2f %s", value, suffix));
            }
        });
        //TODO: set current value or default

        int titleId = settingsKey[1];
        builder.setTitle(titleId)
                .setView(view)
                .setPositiveButton(R.string.OK, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ctx.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //TODO: set value to var and save it to settings
                                float value = ((float)valuePicker.getCurrentValue()) / 10;
                                if (value < 0) {
                                    value = 0;
                                    valuePicker.setCurrentValue(0);
                                }
                                btn.setText(String.format("%.2f %s", value, suffix));
                            }
                        });
                    }
                })
                .setNegativeButton(R.string.CANCELL, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .create()
                .show();
    }
}

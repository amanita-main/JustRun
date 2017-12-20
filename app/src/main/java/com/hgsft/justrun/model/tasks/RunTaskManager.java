package com.hgsft.justrun.model.tasks;

import android.os.Handler;
import android.os.Looper;

import com.hgsft.justrun.model.Data;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Ilya on 19.12.2017.
 */

public class RunTaskManager {

    public enum Mode {
        BASIC,
        DISTANCE,
        SPEED,
        TIME,
        ADVANCED,
    }

    private static RunTaskManager instance = new RunTaskManager();

    private IChangedStateListener.State state = IChangedStateListener.State.STOPPED;
    private IChangedStateListener listener = null;
    //for safe non ui thread calls
    private Handler handler = new Handler(Looper.getMainLooper());
    private Timer timer;
    private TimerTask timerTask = new TimerTask() {
        @Override
        public void run() {
            if (currentTask != null) {
                if (currentTask.isTaskCompleted()) {
                    nextTask();
                }
            }
        }
    };
    private IValueGetter valueGetter;
    private ArrayDeque<RunTask> tasks = new ArrayDeque<>();
    private RunTask currentTask = null;
    private Mode currentMode;

    public static void setOnChangedStateListener(IChangedStateListener listener) {
        instance.listener = listener;
    }

    public static IChangedStateListener.State getCurrentState() {
        return instance.state;
    }

    public static void changeMode(Mode mode) {
        instance.currentMode = mode;
    }

    public static void setTasks(IValueGetter valueGetter, RunTask[] tasks) {
        if (valueGetter != null) instance.valueGetter = valueGetter;
        if (tasks != null) instance.tasks.addAll(Arrays.asList(tasks));
    }

    public static void start() {
        if (instance.currentTask == null) {
            instance.nextTask();
        }
        if (instance.state == IChangedStateListener.State.FINISHED || instance.state == IChangedStateListener.State.STOPPED) {
            instance.timer.scheduleAtFixedRate(instance.timerTask,0, Data.getCurrent().getSettings().getCheckPeriod());
        } else {
            instance.timerTask.run();
        }
    }

    public static void stop() {
        //TODO: request stop state due to timer running?
        //instance.timerTask.cancel();
        instance.timer.cancel();
        //??
        instance.timer.purge();
        instance.currentTask.stop();
        instance.currentTask = null;
        instance.tasks.clear();
        instance.changeState(IChangedStateListener.State.STOPPED);
    }

    public static void pause() {
        //TODO: request pause state due to timer running?
        instance.timer.cancel();
        //instance.timerTask.cancel();
        instance.currentTask.pause();
        instance.changeState(IChangedStateListener.State.PAUSED);
    }

    private void changeState(final IChangedStateListener.State state) {
        if (this.state != state) {
            this.state = state;
            this.handler.post(new Runnable() {
                @Override
                public void run() {
                    if (instance.listener != null) instance.listener.onChangedState(state);
                }
            });
        }
    }

    private void nextTask() {
        currentTask = tasks.remove();
        if (currentTask == null) {
            if (tasks.size() == 0) {
                changeState(IChangedStateListener.State.FINISHED);
            } else {
                nextTask();
            }
        }
    }
}

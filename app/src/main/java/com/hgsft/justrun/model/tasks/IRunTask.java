package com.hgsft.justrun.model.tasks;

/**
 * Created by Ilya on 18.12.2017.
 */

public interface IRunTask {
    enum RunState {
        STARTED,
        PAUSED,
        STOPPED
    }
    public void start();
    public void pause();
    public void stop();
    public RunState getState();
    public boolean isTaskCompleted();
}

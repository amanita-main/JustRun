package com.hgsft.justrun.model.tasks;

/**
 * Created by Ilya on 19.12.2017.
 */

public interface IChangedStateListener {
    enum State {
        PAUSED,
        STARTED,
        STOPPED,
        FINISHED
    }
    public void onChangedState(State state);
}

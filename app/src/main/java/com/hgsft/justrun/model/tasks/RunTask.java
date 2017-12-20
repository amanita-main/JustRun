package com.hgsft.justrun.model.tasks;

import com.hgsft.justrun.model.Rule;

/**
 * Created by Ilya on 19.12.2017.
 */

public class RunTask implements IRunTask {

    private Rule[] rules;
    private RunState state = RunState.STOPPED;
    private IValueGetter valueGetter;
    public RunTask(Rule[] rules, IValueGetter valueGetter) {
        this.rules = rules;
        this.valueGetter = valueGetter;
    }

    @Override
    public void start() {
        this.state = RunState.STARTED;
    }

    @Override
    public void pause() {
        this.state = RunState.PAUSED;
    }

    @Override
    public void stop() {
        this.state = RunState.STOPPED;
    }

    @Override
    public RunState getState() {
        return state;
    }

    @Override
    public boolean isTaskCompleted() {
        boolean result = true;
        for (Rule rule: rules) {
            result = result && rule.check(valueGetter.getValue());
        }
        return result;
    }

}

package com.hgsft.justrun.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ilya on 18.12.2017.
 */

/**
 * Main settings class.
 */
public class Settings {

    enum RunModes {
        BASIC,
        DISTANCE,
        TIME,
        SPEED,
        //TODO: add advanced mode. Commented due I not sure about implementation
        //ADVANCED,
    }

    public static Settings getDefault() {
        Settings settings  = new Settings();
        settings.runSettings = new HashMap<RunModes, Rule[]>() {
            {
                this.put(RunModes.DISTANCE, new Rule[] {
                    new Rule(50f, Rule.RuleType.MORE_OR_EQUAL), //5.0 km * 10 for float show
                });
                this.put(RunModes.TIME, new Rule[] {
                        new Rule(30f, Rule.RuleType.MORE_OR_EQUAL), //30 munutes
                });
                this.put(RunModes.SPEED, new Rule[] {
                        new Rule(330f, Rule.RuleType.MORE_OR_EQUAL), //330 secs per km
                        new Rule(390f, Rule.RuleType.LESS_OR_EQUAL), //390 secs per km
                });

                //TODO: ADVANCED??
            }
        };
        settings.checkPeriod = 500; //500 ms
        settings.countDownTimer = 3000; //3000ms

        return  settings;
    }

    private Map<RunModes, Rule[]> runSettings;
    public Rule[] getRunSettings(RunModes mode) {
        assert(!this.runSettings.containsKey(mode));
        return this.runSettings.get(mode);
    }

    private int checkPeriod;
    public int getCheckPeriod() { return this.checkPeriod; }
    private int countDownTimer;
    public int getCountDownTimer() { return this.countDownTimer; }
}

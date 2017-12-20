package com.hgsft.justrun.model;

/**
 * Created by Ilya on 18.12.2017.
 */

public class Rule {
    enum RuleType {
        LESS,
        LESS_OR_EQUAL,
        EQUAL, //??
        MORE,
        MORE_OR_EQUAL,
    }

    private float value;
    public float getValue() { return this.value; }
    public void setValue(float value) { this.value = value; }
    private RuleType type;

    public Rule(float value, RuleType type) {
        this.value = value;
        this.type = type;
    }

    public boolean check(int checkValue) {
        return check((float)checkValue);
    }

    public boolean check(float checkValue) {
        switch (this.type) {
            case LESS:
                return checkValue < this.value;
            case LESS_OR_EQUAL:
                return checkValue <= this.value;
            case EQUAL:
                return checkValue == this.value; //??
            case MORE:
                return checkValue > this.value;
            case MORE_OR_EQUAL:
                return checkValue >= this.value;
        }
        return false;
    }
}

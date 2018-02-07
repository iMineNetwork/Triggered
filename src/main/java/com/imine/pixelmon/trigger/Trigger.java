package com.imine.pixelmon.trigger;

import com.imine.pixelmon.trigger.action.Action;
import com.imine.pixelmon.trigger.condition.Condition;

import java.util.List;

public class Trigger {

    //Triggers will be activated once at least one of these conditions has been met.
    private List<Condition> conditions;
    //All these actions will occur when the trigger activates
    private List<Action> actions;

    private Interval repeat;

    public Trigger() {
    }

    public Trigger(List<Condition> conditions, List<Action> actions, Interval repeat) {
        this.conditions = conditions;
        this.actions = actions;
        this.repeat = repeat;
    }

    public List<Condition> getConditions() {
        return conditions;
    }

    public void setConditions(List<Condition> conditions) {
        this.conditions = conditions;
    }

    public List<Action> getActions() {
        return actions;
    }

    public void setActions(List<Action> actions) {
        this.actions = actions;
    }

    public Interval getRepeat() {
        return repeat;
    }

    public void setRepeat(Interval repeat) {
        this.repeat = repeat;
    }

    @Override
    public String toString() {
        return "Trigger{" +
                "conditions=" + conditions +
                ", actions=" + actions +
                ", repeat=" + repeat +
                '}';
    }
}

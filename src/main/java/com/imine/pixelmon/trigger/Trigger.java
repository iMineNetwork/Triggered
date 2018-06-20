package com.imine.pixelmon.trigger;

import com.imine.pixelmon.trigger.action.Action;
import com.imine.pixelmon.trigger.condition.Condition;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class Trigger {

    private String id;
    private List<Condition> conditions;
    private List<Action> actions;
    private Interval repeat;
    private Cooldown cooldown;

    public Trigger() {
    }

    public Trigger(String id, List<Condition> conditions, List<Action> actions, Interval repeat, Cooldown cooldown) {
        this.conditions = conditions;
        this.actions = actions;
        this.repeat = repeat;
        this.cooldown = cooldown;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public Optional<Cooldown> getCooldown() {
        return Optional.ofNullable(cooldown);
    }

    public void setCooldown(Cooldown cooldown) {
        this.cooldown = cooldown;
    }

    @Override
    public String toString() {
        return "Trigger{" +
                "id='" + id + '\'' +
                ", conditions=" + conditions +
                ", actions=" + actions +
                ", repeat=" + repeat +
                '}';
    }
}

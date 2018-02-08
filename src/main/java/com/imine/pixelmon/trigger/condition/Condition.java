package com.imine.pixelmon.trigger.condition;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.imine.pixelmon.trigger.requirement.Requirement;

import java.util.List;

@JsonTypeInfo(use = JsonTypeInfo.Id.MINIMAL_CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@class")
public abstract class Condition {

    private List<Requirement> requirements;

    public Condition() {
    }

    public Condition(List<Requirement> requirements) {
        this.requirements = requirements;
    }

    public List<Requirement> getRequirements() {
        return requirements;
    }

    public void setRequirements(List<Requirement> requirements) {
        this.requirements = requirements;
    }
}

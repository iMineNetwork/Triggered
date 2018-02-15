package com.imine.pixelmon.trigger.condition;

import com.imine.pixelmon.trigger.requirement.Requirement;
import org.spongepowered.api.entity.Entity;

import java.util.List;

public abstract class EntityInteractCondition extends Condition {

    public EntityInteractCondition(List<Requirement> requirements) {
        super(requirements);
    }

    public abstract boolean entityMatches(Entity entity);
}

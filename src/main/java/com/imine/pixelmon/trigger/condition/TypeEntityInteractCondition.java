package com.imine.pixelmon.trigger.condition;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.imine.pixelmon.trigger.requirement.Requirement;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.EntityType;

import java.util.List;

public class TypeEntityInteractCondition extends EntityInteractCondition {

    private final EntityType entityType;

    @JsonCreator
    public TypeEntityInteractCondition(@JsonProperty("requirements") List<Requirement> requirements, @JsonProperty("entityType") EntityType entityType) {
        super(requirements);
        this.entityType = entityType;
    }

    public EntityType getEntityType() {
        return entityType;
    }

    @Override
    public boolean entityMatches(Entity entity) {
        return entity.getType().equals(entityType);
    }
}

package com.imine.pixelmon.trigger.condition;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.imine.pixelmon.trigger.requirement.Requirement;
import org.spongepowered.api.entity.Entity;

import java.util.List;
import java.util.UUID;

public class UUIDEntityInteractCondition extends EntityInteractCondition {

    private final UUID uuid;

    @JsonCreator
    public UUIDEntityInteractCondition(@JsonProperty("requirements") List<Requirement> requirements, @JsonProperty("uuid") UUID uuid) {
        super(requirements);
        this.uuid = uuid;
    }

    public UUID getUuid() {
        return uuid;
    }

    @Override
    public boolean entityMatches(Entity entity) {
        return entity.getUniqueId().equals(uuid);
    }
}

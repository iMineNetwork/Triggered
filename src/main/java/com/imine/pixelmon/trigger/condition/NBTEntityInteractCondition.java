package com.imine.pixelmon.trigger.condition;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.imine.pixelmon.trigger.requirement.Requirement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongepowered.api.data.DataQuery;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.EntityType;

import java.util.List;

public class NBTEntityInteractCondition extends EntityInteractCondition {

    private static final Logger logger = LoggerFactory.getLogger(NBTEntityInteractCondition.class);

    private final String key;
    private final String value;

    @JsonCreator
    public NBTEntityInteractCondition(@JsonProperty("requirements") List<Requirement> requirements, @JsonProperty("key") String key, @JsonProperty("value") String value) {
        super(requirements);
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean entityMatches(Entity entity) {
        logger.info("Matching entity:");
        logger.info("Looking for: ({}: {})", key, value);
        Object object = entity.toContainer().get(DataQuery.of(key));
        entity.toContainer().getValues(true).forEach((k, v) -> {
            logger.info("Found value: ({}: {})", k, v);
        });
        return object.toString().equals(value);
    }
}

package com.imine.pixelmon.trigger.requirement;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.spongepowered.api.entity.Entity;

@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS, include= JsonTypeInfo.As.PROPERTY, property="@class")
public interface Requirement {

    boolean entityMeetsRequirement(Entity entity);
}

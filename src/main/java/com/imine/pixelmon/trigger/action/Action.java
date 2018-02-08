package com.imine.pixelmon.trigger.action;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.spongepowered.api.entity.Entity;

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@class")
public interface Action {

    void perform(Entity entity);
}

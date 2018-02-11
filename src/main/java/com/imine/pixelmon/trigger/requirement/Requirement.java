package com.imine.pixelmon.trigger.requirement;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.living.player.Player;

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@class")
public interface Requirement {

    boolean meetsRequirement(Player player);
}

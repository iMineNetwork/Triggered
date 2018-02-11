package com.imine.pixelmon.trigger.requirement;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.imine.pixelmon.integration.PixelmonIntegration;
import org.spongepowered.api.entity.living.player.Player;

public abstract class AbstractPixelmonTeamCountRequirement implements Requirement {

    private final int count;

    public AbstractPixelmonTeamCountRequirement(int count) {
        this.count = count;
    }

    public int getCountPixelmonCount() {
        return count;
    }
}

package com.imine.pixelmon.trigger.requirement;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.imine.pixelmon.integration.PixelmonIntegration;
import org.spongepowered.api.entity.living.player.Player;

public class MaxPixelmonTeamCountRequirement extends AbstractPixelmonTeamCountRequirement {

    @JsonCreator
    public MaxPixelmonTeamCountRequirement(@JsonProperty("count") int count) {
        super(count);
    }

    @Override
    public boolean meetsRequirement(Player player) {
        return PixelmonIntegration.getPlayerPixelmonCount(player) <= getCountPixelmonCount();
    }
}

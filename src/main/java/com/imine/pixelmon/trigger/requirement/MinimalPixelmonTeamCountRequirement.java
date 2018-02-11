package com.imine.pixelmon.trigger.requirement;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.imine.pixelmon.integration.PixelmonIntegration;
import org.spongepowered.api.entity.living.player.Player;

public class MinimalPixelmonTeamCountRequirement extends AbstractPixelmonTeamCountRequirement {

    @JsonCreator
    public MinimalPixelmonTeamCountRequirement(@JsonProperty("count") int count) {
        super(count);
    }

    @Override
    public boolean meetsRequirement(Player player) {
        return PixelmonIntegration.getPlayerPixelmonCount(player) >= getCountPixelmonCount();
    }
}

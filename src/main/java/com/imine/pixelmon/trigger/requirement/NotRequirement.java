package com.imine.pixelmon.trigger.requirement;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.spongepowered.api.entity.living.player.Player;

public class NotRequirement implements Requirement {

    private final Requirement requirement;

    @JsonCreator
    public NotRequirement(@JsonProperty("requirement") Requirement requirement) {
        this.requirement = requirement;
    }

    public Requirement getRequirement() {
        return requirement;
    }

    @Override
    public boolean meetsRequirement(Player player) {
        return !requirement.meetsRequirement(player);
    }
}

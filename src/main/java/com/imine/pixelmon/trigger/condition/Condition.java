package com.imine.pixelmon.trigger.condition;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.imine.pixelmon.trigger.requirement.Requirement;
import org.spongepowered.api.entity.living.player.Player;

import java.util.List;

@JsonTypeInfo(use = JsonTypeInfo.Id.MINIMAL_CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@class")
public abstract class Condition {

    private final List<Requirement> requirements;

    @JsonCreator
    public Condition(@JsonProperty("requirements") List<Requirement> requirements) {
        this.requirements = requirements;
    }

    public List<Requirement> getRequirements() {
        return requirements;
    }

    public boolean matchesRequirements(Player player) {
        return requirements != null && requirements.parallelStream().allMatch(requirement -> requirement.meetsRequirement(player));
    }

}

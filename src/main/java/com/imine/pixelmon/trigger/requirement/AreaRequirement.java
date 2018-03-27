package com.imine.pixelmon.trigger.requirement;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.flowpowered.math.vector.Vector3d;
import com.imine.pixelmon.trigger.condition.Condition;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.util.AABB;
import org.spongepowered.api.world.World;

import java.util.List;
import java.util.Optional;

public class AreaRequirement implements Requirement {

    private final AABB area;
    private final String world;

    @JsonCreator
    public AreaRequirement(@JsonProperty("area") AABB area, @JsonProperty("world") String world) {
        this.area = area;
        this.world = world;
    }

    public boolean meetsRequirement(Player player) {
        Optional<World> oWorld = Sponge.getServer().getWorld(world);
        return area.contains(player.getLocation().getPosition())
                && oWorld.equals(Optional.of(player.getLocation().getExtent()));
    }

    public AABB getArea() {
        return area;
    }

    public String getWorld() {
        return world;
    }

    @Override
    public String toString() {
        return "AreaCondition{" +
                "area=" + area +
                ", worldName=" + world +
                '}';
    }
}

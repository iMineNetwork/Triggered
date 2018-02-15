package com.imine.pixelmon.trigger.condition;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.flowpowered.math.vector.Vector3d;
import com.imine.pixelmon.trigger.requirement.Requirement;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.util.AABB;
import org.spongepowered.api.world.World;

import java.util.List;
import java.util.Optional;

public class AreaCondition extends Condition {

    private final AABB area;
    private final String worldName;

    @JsonCreator
    public AreaCondition(@JsonProperty("requirements") List<Requirement> requirements, @JsonProperty("area") AABB area, @JsonProperty("worldName") String worldName) {
        super(requirements);
        this.area = area;
        this.worldName = worldName;
    }

    public boolean isInArea(Vector3d vector3d) {
        Optional<World> oWorld = Sponge.getServer().getWorld(this.worldName);
        return area.contains(vector3d)
                && oWorld.isPresent()
                && oWorld.get().getName().equalsIgnoreCase(worldName);
    }

    public AABB getArea() {
        return area;
    }

    public String getWorld() {
        return worldName;
    }

    @Override
    public String toString() {
        return "AreaCondition{" +
                "area=" + area +
                ", worldName=" + worldName +
                '}';
    }
}

package com.imine.pixelmon.trigger.condition;

import com.flowpowered.math.vector.Vector3d;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.util.AABB;
import org.spongepowered.api.world.World;

import java.util.Optional;

public class AreaCondition extends Condition {

    private AABB area;
    private String worldName;

    public AreaCondition() {
    }

    public AreaCondition(AABB area, String worldName) {
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

    public void setArea(AABB area) {
        this.area = area;
    }

    public String getWorld() {
        return worldName;
    }

    public void setWorld(String worldName) {
        this.worldName = worldName;
    }

    @Override
    public String toString() {
        return "AreaCondition{" +
                "area=" + area +
                ", worldName=" + worldName +
                '}';
    }
}

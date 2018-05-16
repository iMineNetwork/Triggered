package com.imine.pixelmon.trigger.condition;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.imine.pixelmon.trigger.requirement.Requirement;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.block.BlockSnapshot;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import java.util.List;
import java.util.Optional;

public class BlockInteractCondition extends Condition implements IPositioned {

    private int x, y, z;
    private String world;

    public BlockInteractCondition(@JsonProperty("requirements") List<Requirement> requirements, @JsonProperty("x") int x, @JsonProperty("y") int y, @JsonProperty("z") int z, @JsonProperty("world") String world) {
        super(requirements);
        this.x = x;
        this.y = y;
        this.z = z;
        this.world = world;
    }

    public boolean blockMatches(BlockSnapshot block) {
        Optional<Location<org.spongepowered.api.world.World>> location = block.getLocation();
        if (location.isPresent()) {
            Location<org.spongepowered.api.world.World> worldLocation = location.get();
            return this.x == worldLocation.getBlockX()
                    && this.y == worldLocation.getBlockY()
                    && this.z == worldLocation.getBlockZ()
                    && this.world.equals(worldLocation.getExtent().getName());
        }
        return false;
    }

    @Override
    public Location getLocation() {
        return Sponge.getServer().getWorld(world)
                .map(world -> new Location(world, x, y, z))
                .orElse(null);
    }
}
package com.imine.pixelmon.trigger.condition;

import com.imine.pixelmon.trigger.requirement.Requirement;
import org.spongepowered.api.block.BlockSnapshot;

import java.util.List;

public abstract class BlockInteractCondition extends Condition {

    public BlockInteractCondition(List<Requirement> requirements) {
        super(requirements);
    }

    public abstract boolean blockMatches(BlockSnapshot block);
}
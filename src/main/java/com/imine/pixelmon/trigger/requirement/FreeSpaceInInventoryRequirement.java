package com.imine.pixelmon.trigger.requirement;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.inventory.Inventory;

import java.util.Optional;

public class FreeSpaceInInventoryRequirement implements Requirement {

    int requiredSlots;

    @JsonCreator
    public FreeSpaceInInventoryRequirement() {
        super();
        this.requiredSlots = 1;
    }

    @JsonCreator
    public FreeSpaceInInventoryRequirement(@JsonProperty("amount") int requiredSlots) {
        super();
        this.requiredSlots = requiredSlots;
    }

    @Override
    public boolean meetsRequirement(Player player) {
        int freeSlots = 0;

        player.getInventory().peek();


        for (Inventory slot : player.getInventory().slots()) {
            if (slot.peek().isPresent()) {
                if (slot.peek().equals(Optional.empty()) || slot.peek().get().getItem() == ItemTypes.NONE) {
                    freeSlots++;
                }
            } else {
                freeSlots++;
            }
        }
        return freeSlots >= requiredSlots;
    }
}

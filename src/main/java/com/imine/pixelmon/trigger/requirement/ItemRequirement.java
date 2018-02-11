package com.imine.pixelmon.trigger.requirement;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.ItemType;

import java.util.Optional;

public class ItemRequirement implements Requirement {

    private String itemType;

    public ItemRequirement() {
    }

    public ItemRequirement(String itemType) {
        this.itemType = itemType;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    @Override
    public boolean meetsRequirement(Player player) {
        Optional<ItemType> oItemType = Sponge.getGame().getRegistry().getType(ItemType.class, itemType);
        if (oItemType.isPresent()) {
            return player.getInventory().contains(oItemType.get());
        } else {
            throw new IllegalArgumentException("ItemType '" + itemType + "' was not registered or does not exist");
        }
    }
}

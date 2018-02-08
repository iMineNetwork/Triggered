package com.imine.pixelmon.trigger.requirement;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.ItemType;

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
    public boolean entityMeetsRequirement(Entity entity) {
        if (entity instanceof Player) {
            Player player = ((Player) entity);
            return (player.getInventory().query(Sponge.getGame().getRegistry().getType(ItemType.class, itemType)) != null);
        }
        return false;
    }
}

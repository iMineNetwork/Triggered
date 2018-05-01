package com.imine.pixelmon.trigger.requirement;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.item.inventory.Slot;
import org.spongepowered.api.item.inventory.entity.PlayerInventory;
import org.spongepowered.api.item.inventory.equipment.EquipmentTypes;
import org.spongepowered.api.item.inventory.query.QueryOperationTypes;

import java.util.Optional;

public class HelmetItemRequirement extends ItemRequirement {

    private static final Logger logger = LoggerFactory.getLogger(HelmetItemRequirement.class);

    @JsonCreator
    public HelmetItemRequirement(@JsonProperty("itemType") String itemType) {
        super(itemType);
    }

    @Override
    public boolean meetsRequirement(Player player) {
        Optional<ItemType> oItemType = Sponge.getGame().getRegistry().getType(ItemType.class, getItemType());
        if (oItemType.isPresent()) {
            Optional<Slot> headgearSlot = ((PlayerInventory) player.getInventory().query(QueryOperationTypes.INVENTORY_TYPE.of(PlayerInventory.class))).getEquipment().getSlot(EquipmentTypes.HEADWEAR);
            if(headgearSlot.isPresent()) {
                return headgearSlot.get().peek().map(ItemStack::getType).equals(oItemType);
            } else {
                logger.warn("PlayerInventory for '{}' had no Helmet Slot", player.getName());
                return false;
            }
        } else {
            throw new IllegalArgumentException("ItemType '" + getItemType() + "' was not registered or does not exist");
        }
    }
}

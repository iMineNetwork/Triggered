package com.imine.pixelmon.trigger.action.entry;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ItemEntry {

    String item;
    int amount;
    int damage;
    String nbt;

    @JsonCreator
    public ItemEntry(@JsonProperty("Item") String name,
                     @JsonProperty("amount") int amount,
                     @JsonProperty("damage") int damage,
                     @JsonProperty("nbt") String nbt) {
        this.item = name;
        this.amount = amount;
        this.damage = damage;
        this.nbt = nbt;
    }

    public String getItem() {
        return item;
    }

    public int getAmount() {
        return amount;
    }

    public int getDamage() {
        return damage;
    }

    public String getNbt() {
        return nbt;
    }
}
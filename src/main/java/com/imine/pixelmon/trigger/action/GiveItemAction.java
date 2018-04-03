package com.imine.pixelmon.trigger.action;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.pixelmonmod.pixelmon.api.dialogue.Dialogue;
import net.minecraft.entity.player.EntityPlayerMP;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.effect.sound.SoundType;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Optional;

public class GiveItemAction implements Action {

    private static final Logger logger = LoggerFactory.getLogger(GiveItemAction.class);
    private static SoundType sound = SoundType.of("custom.itemget");

    private final ItemType itemType;
    private final int amount;
    private final boolean unbreakable;
    private final int durability;

    @JsonCreator
    public GiveItemAction(@JsonProperty("item") String itemType,
                          @JsonProperty("amount") int amount,
                          @JsonProperty("unbreakable") boolean unbreakable,
                          @JsonProperty("durability") int durability) {


        logger.info("Creating item: {} {} {} {}", itemType, amount, unbreakable, durability);

        Optional<ItemType> oItemType = Sponge.getGame().getRegistry().getType(ItemType.class, itemType);
        ItemType itemType1;

        this.amount = amount > 0 ? amount : 1;
        this.unbreakable = unbreakable;
        this.durability = durability;

        if (oItemType.isPresent()) {
            this.itemType = oItemType.get();
        } else {
            this.itemType = ItemTypes.STICK;
            logger.warn("No item found bu type " + itemType);
            return;
        }


    }

    @Override
    public void perform(Player player) {
        ItemStack itemStack = ItemStack.builder()
                .itemType(itemType)
                .quantity(amount)
                .add(Keys.UNBREAKABLE, unbreakable)
                .add(Keys.ITEM_DURABILITY, durability)
                .build();

        ArrayList<Dialogue> dialogueList = new ArrayList<>();
        dialogueList.add(
                Dialogue.builder()
                        .setName("")
                        .setText("You received " +
                                (startsWithVowel(itemType.getName()) ? "an " : "a ") +

                                //itemname without minecraft: or pixelmon:
                                itemType.getName().substring(itemType.getName().indexOf(":") + 1)
                                + "!")
                        .build()
        );

        logger.info("Giving item '{}' to player '{}'", itemStack.getItem(), player.getName());

        Dialogue.setPlayerDialogueData((EntityPlayerMP) player, dialogueList, true);
        player.getInventory().offer(itemStack);
        player.playSound(sound, player.getLocation().getPosition(), 50, 1);

        logger.info("Giving item {} was successful", itemType.getName());
    }

    private boolean startsWithVowel(String string) {
        return string.startsWith("a") ||
                string.startsWith("e") ||
                string.startsWith("i") ||
                string.startsWith("o") ||
                string.startsWith("u");
    }
}

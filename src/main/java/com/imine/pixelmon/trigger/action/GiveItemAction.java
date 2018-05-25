package com.imine.pixelmon.trigger.action;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.imine.pixelmon.TriggeredPlugin;
import com.pixelmonmod.pixelmon.api.dialogue.Dialogue;
import net.minecraft.entity.player.EntityPlayerMP;
import nl.imine.pixelmon.packingmule.api.GiveItemAPI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.effect.sound.SoundType;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.item.inventory.transaction.InventoryTransactionResult;
import org.spongepowered.api.scheduler.Task;

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

        Optional<ItemType> oItemType = Sponge.getGame().getRegistry().getType(ItemType.class, itemType);

        this.amount = amount > 0 ? amount : 1;
        this.unbreakable = unbreakable;
        this.durability = durability;

        if (oItemType.isPresent()) {
            this.itemType = oItemType.get();
        } else {
            this.itemType = ItemTypes.STICK;
            logger.warn("No item found with type " + itemType);
            return;
        }


    }

    @Override
    public void perform(Player player) {
        ItemStack itemStack = ItemStack.builder()
                .itemType(itemType)
                .quantity(amount)
                .add(Keys.UNBREAKABLE, unbreakable)
                .build();

        GiveItemAPI.getGiveItemAPI().giveItemToPlayer(player, itemStack);

        player.playSound(sound, player.getLocation().getPosition(), 50, 1);
        ArrayList<Dialogue> dialogueList = new ArrayList<>();

        Task.builder().execute(() -> sendDialogueToPlayer((EntityPlayerMP) player, dialogueList)).submit(TriggeredPlugin.getPluginInstance());
    }

    private void sendDialogueToPlayer(EntityPlayerMP player, ArrayList<Dialogue> dialogueList) {
        dialogueList.add(
                Dialogue.builder()
                        .setName(" ")
                        .setText("You received " +
                                (startsWithVowel(itemType.getName()) ? "an " : "a ") +

                                //itemname without minecraft: or pixelmon:
                                itemType.getName().substring(itemType.getName().indexOf(":") + 1)
                                + "!")
                        .build()
        );
        Dialogue.setPlayerDialogueData(player, dialogueList, true);
    }

    private boolean startsWithVowel(String string) {
        return string.startsWith("a") ||
                string.startsWith("e") ||
                string.startsWith("i") ||
                string.startsWith("o") ||
                string.startsWith("u");
    }
}

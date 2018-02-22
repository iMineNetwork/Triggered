package com.imine.pixelmon.trigger.action;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.imine.pixelmon.trigger.action.entry.DialogueEntry;
import com.imine.pixelmon.trigger.action.entry.ItemEntry;
import com.imine.pixelmon.trigger.action.entry.SoundEntry;
import com.pixelmonmod.pixelmon.api.dialogue.Dialogue;
import net.minecraft.entity.player.EntityPlayerMP;
import org.apache.commons.lang3.text.StrSubstitutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GiveItemAction extends ContextAction {

    private static final Logger logger = LoggerFactory.getLogger(GiveItemAction.class);

    private final List<DialogueEntry> dialogueEntries;
    private final List<ItemEntry> itemEntries;
    private final List<SoundEntry> soundentries;

    public GiveItemAction(@JsonProperty("dialogueEntries") List<DialogueEntry> dialogueEntries
            , @JsonProperty("ItemEntries") List<ItemEntry> itemEntries,
                          @JsonProperty("sound") List<SoundEntry> soundentries
                          ) {
        this.dialogueEntries = dialogueEntries;
        this.itemEntries = itemEntries;
        this.soundentries = soundentries;
    }

    public List<DialogueEntry> getDialogueEntries() {
        return dialogueEntries;
    }

    public List<ItemEntry> getItemEntries() {
        return itemEntries;
    }

    public List<SoundEntry> getSoundentries() {
        return soundentries;
    }

    @Override
    public void perform(Player player) {
        ArrayList<Dialogue> dialogueList = dialogueEntries.stream()
                .map(dialogueEntry -> Dialogue.builder()
                        .setName(StrSubstitutor.replace(dialogueEntry.getName(), buildContext(player)))
                        .setText(StrSubstitutor.replace(dialogueEntry.getMessage(), buildContext(player)))
                        .build())
                .collect(Collectors.toCollection(ArrayList::new));
        Dialogue.setPlayerDialogueData((EntityPlayerMP) player, dialogueList, true);

        itemEntries.forEach(itemEntry -> {
            String preparedCommand = "give " +
                    player.getName() + " " +
                    itemEntry.getItem() + " " +
                    itemEntry.getAmount()+ " " +
                    itemEntry.getNbt();
                    logger.info("Running command '{}' for player '{}'", preparedCommand, player.getName());
            Sponge.getGame().getCommandManager().process(Sponge.getServer().getConsole(), preparedCommand);
        });

        soundentries.forEach(soundEntry -> {
            player.playSound(
                    soundEntry.getSoundType(),
                    soundEntry.getSoundCategory(),
                    soundEntry.getPosition(),
                    soundEntry.getVolume(),
                    soundEntry.getPitch(),
                    soundEntry.getMinimumvolume());
        });
    }

}

package com.imine.pixelmon.component;

import com.imine.pixelmon.trigger.action.Action;
import com.pixelmonmod.pixelmon.api.dialogue.Dialogue;
import net.minecraft.entity.player.EntityPlayerMP;
import org.spongepowered.api.entity.living.player.Player;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class DialogueHandler {

    private static DialogueHandler instance;

    private Map<UUID, List<Action>> playerDialogueCloseActions;

    public DialogueHandler() {
        this.playerDialogueCloseActions = new HashMap<>();
    }

    public static DialogueHandler getInstance() {
        if (instance == null) {
            instance = new DialogueHandler();
        }
        return instance;
    }

    public void openDialogueForPlayer(Player player, List<Dialogue> dialogueList, List<Action> actions) {
        Dialogue.setPlayerDialogueData((EntityPlayerMP) player, dialogueList, true);
        if (actions != null && !actions.isEmpty()) {
            playerDialogueCloseActions.put(player.getUniqueId(), actions);
        }
    }

    public void handleCloseForPlayer(Player player) {
        List<Action> actions = playerDialogueCloseActions.remove(player.getUniqueId());
        if (actions != null && !actions.isEmpty()) {
            actions.forEach(action -> action.perform(player));
        }
    }
}

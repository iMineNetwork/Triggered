package com.imine.pixelmon.trigger.action;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.imine.pixelmon.component.DialogueHandler;
import com.pixelmonmod.pixelmon.api.dialogue.Dialogue;
import org.apache.commons.lang3.text.StrSubstitutor;
import org.spongepowered.api.entity.living.player.Player;

import java.util.List;
import java.util.stream.Collectors;

public class DialogueAction extends ContextAction {

    private final List<DialogueEntry> dialogueEntries;
    private final List<Action> closeActions;

    @JsonCreator
    public DialogueAction(@JsonProperty("dialogueEntries") List<DialogueEntry> dialogueEntries, @JsonProperty("closeActions") List<Action> closeActions) {
        this.dialogueEntries = dialogueEntries;
        this.closeActions = closeActions;
    }

    public List<DialogueEntry> getDialogueEntries() {
        return dialogueEntries;
    }

    @Override
    public void perform(Player player) {
        List<Dialogue> dialogueList = dialogueEntries.stream()
                .map(dialogueEntry -> Dialogue.builder()
                        .setName(StrSubstitutor.replace(dialogueEntry.getName(), buildContext(player)))
                        .setText(StrSubstitutor.replace(dialogueEntry.getMessage(), buildContext(player)))
                        .build())
                .collect(Collectors.toList());
        DialogueHandler.getInstance().openDialogueForPlayer(player, dialogueList, closeActions);
    }

    public static class DialogueEntry {

        private final String name;
        private final String message;

        @JsonCreator
        public DialogueEntry(@JsonProperty("name") String name, @JsonProperty("message") String message) {
            this.name = name;
            this.message = message;
        }

        public String getName() {
            return name;
        }

        public String getMessage() {
            return message;
        }
    }
}

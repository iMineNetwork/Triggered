package com.imine.pixelmon.trigger.requirement;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.imine.pixelmon.service.PlayerTriggerActivationService;
import org.spongepowered.api.entity.living.player.Player;

public class TriggerActivationRequirement implements Requirement {

    private static PlayerTriggerActivationService playerTriggerActivationService;

    private final String triggerId;

    @JsonCreator
    public TriggerActivationRequirement(@JsonProperty("triggerId") String triggerId) {
        this.triggerId = triggerId;
    }

    public static void setPlayerTriggerActivationService(PlayerTriggerActivationService playerTriggerActivationService) {
        TriggerActivationRequirement.playerTriggerActivationService = playerTriggerActivationService;
    }

    public String getTriggerId() {
        return triggerId;
    }

    @Override
    public boolean meetsRequirement(Player player) {
        return playerTriggerActivationService.getAll().stream()
                .anyMatch(playerTriggerActivation -> playerTriggerActivation.getPlayerId().equals(player.getUniqueId())
                        && playerTriggerActivation.getTriggerId().equals(triggerId));
    }
}

package com.imine.pixelmon.model;

import java.util.UUID;

public class PlayerTriggerActivation {

    private UUID playerId;
    private String triggerId;

    public PlayerTriggerActivation() {
    }

    public PlayerTriggerActivation(UUID playerId, String triggerId) {
        this.playerId = playerId;
        this.triggerId = triggerId;
    }

    public UUID getPlayerId() {
        return playerId;
    }

    public void setPlayerId(UUID playerId) {
        this.playerId = playerId;
    }

    public String getTriggerId() {
        return triggerId;
    }

    public void setTriggerId(String triggerId) {
        this.triggerId = triggerId;
    }
}

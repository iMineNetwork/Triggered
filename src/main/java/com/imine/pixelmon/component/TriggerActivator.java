package com.imine.pixelmon.component;

import com.imine.pixelmon.model.PlayerTriggerActivation;
import com.imine.pixelmon.service.PlayerTriggerActivationService;
import com.imine.pixelmon.trigger.Cooldown;
import com.imine.pixelmon.trigger.Interval;
import com.imine.pixelmon.trigger.Trigger;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.serializer.TextSerializers;

import java.util.Optional;

public class TriggerActivator {

    private final GlobalCooldownHandler globalCooldownHandler;
    private final PlayerTriggerActivationService playerTriggerActivationService;

    public TriggerActivator(GlobalCooldownHandler globalCooldownHandler, PlayerTriggerActivationService playerTriggerActivationService) {
        this.globalCooldownHandler = globalCooldownHandler;
        this.playerTriggerActivationService = playerTriggerActivationService;
    }

    public void activateTriggerForPlayer(Trigger trigger, Player player) {
        if(globalCooldownHandler.isTriggerInCooldown(trigger))
            sendPlayerCooldownMessage(player, trigger);
        else
            activateTrigger(trigger, player);
    }

    private void sendPlayerCooldownMessage(Player player, Trigger trigger) {
        trigger.getCooldown()
                .map(Cooldown::getMessage)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(TextSerializers.FORMATTING_CODE::deserialize).ifPresent(player::sendMessage);
    }

    private void activateTrigger(Trigger trigger, Player player) {
        trigger.getCooldown().ifPresent(cooldown -> globalCooldownHandler.cooldownTrigger(trigger));
        trigger.getActions().forEach(action -> action.perform(player));
        if (trigger.getRepeat().equals(Interval.ONCE)) {
            playerTriggerActivationService.add(new PlayerTriggerActivation(player.getUniqueId(), trigger.getId()));
        }
    }
}



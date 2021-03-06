package com.imine.pixelmon.event;

import com.imine.pixelmon.component.DialogueHandler;
import com.imine.pixelmon.component.TriggerActivator;
import com.imine.pixelmon.service.PlayerTriggerActivationService;
import com.imine.pixelmon.service.TriggerService;
import com.imine.pixelmon.trigger.Interval;
import com.imine.pixelmon.trigger.Trigger;
import com.imine.pixelmon.trigger.condition.Condition;
import com.imine.pixelmon.trigger.condition.battle.PlayerBattleEndCondition;
import com.pixelmonmod.pixelmon.api.events.battles.BattleEndEvent;
import com.pixelmonmod.pixelmon.api.events.dialogue.DialogueEndedEvent;
import com.pixelmonmod.pixelmon.battles.controller.participants.BattleParticipant;
import com.pixelmonmod.pixelmon.enums.battle.BattleResults;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.spongepowered.api.entity.living.player.Player;

import java.util.Set;

public class PixelmonListener {

    private final TriggerService triggerService;
    private final PlayerTriggerActivationService playerTriggerActivationService;
    private final TriggerActivator triggerActivator;

    public PixelmonListener(TriggerService triggerService, PlayerTriggerActivationService playerTriggerActivationService, TriggerActivator triggerActivator) {
        this.triggerService = triggerService;
        this.playerTriggerActivationService = playerTriggerActivationService;
        this.triggerActivator = triggerActivator;
    }

    @SubscribeEvent
    public void onPlayerBattleEnd(BattleEndEvent battleEndEvent) {
        battleEndEvent.results.forEach((k, v) -> {
            if (k.getEntity() instanceof Player) {
                handleBattleEndForPlayer((Player) k.getEntity(), battleEndEvent.results.keySet(), v);
            }
        });
    }

    @SubscribeEvent
    public void onDialogueCloseEvent(DialogueEndedEvent dialogueEndedEvent) {
        DialogueHandler.getInstance().handleCloseForPlayer((Player) dialogueEndedEvent.player);
    }

    private boolean shouldTriggerRunForPlayer(Trigger trigger, Player player) {
        return Interval.ALWAYS.equals(trigger.getRepeat())
                || playerTriggerActivationService.getAll().stream()
                .noneMatch(playerTriggerActivation -> playerTriggerActivation.getPlayerId().equals(player.getUniqueId())
                        && playerTriggerActivation.getTriggerId().equals(trigger.getId()));
    }

    public void handleBattleEndForPlayer(Player player, Set<BattleParticipant> participants, BattleResults resultForPlayer) {
        for (Trigger trigger : triggerService.getAll()) {
            if (shouldTriggerRunForPlayer(trigger, player)) {
                for (Condition condition : trigger.getConditions()) {
                    if (condition instanceof PlayerBattleEndCondition) {
                        if (((PlayerBattleEndCondition) condition).matchesCondition(player, participants, resultForPlayer)) {
                            if (condition.matchesRequirements(player)) {
                                triggerActivator.activateTriggerForPlayer(trigger, player);
                                break;
                            }
                        }
                    }
                }
            }
        }
    }

}

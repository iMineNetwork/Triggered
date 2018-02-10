package com.imine.pixelmon.event;

import com.imine.pixelmon.service.PlayerTriggerActivationService;
import com.imine.pixelmon.service.TriggerService;
import com.imine.pixelmon.trigger.Interval;
import com.imine.pixelmon.trigger.Trigger;
import com.imine.pixelmon.trigger.action.Action;
import com.imine.pixelmon.trigger.condition.AreaCondition;
import com.imine.pixelmon.trigger.condition.Condition;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.entity.MoveEntityEvent;

public class TriggerEventListener {

    private final TriggerService triggerService;
    private final PlayerTriggerActivationService playerTriggerActivationService;

    public TriggerEventListener(TriggerService triggerService, PlayerTriggerActivationService playerTriggerActivationService) {
        this.triggerService = triggerService;
        this.playerTriggerActivationService = playerTriggerActivationService;
    }

    @Listener
    public void onEntityMove(MoveEntityEvent moveEntityEvent) {
        if (moveEntityEvent.getTargetEntity() instanceof Player) {
            Player player = ((Player) moveEntityEvent.getTargetEntity());
            for (Trigger trigger : triggerService.loadAll()) {
                if (shouldTriggerRunForPlayer(trigger, player)) {
                    for (Condition condition : trigger.getConditions()) {
                        if (condition.getRequirements().stream().allMatch(requirement -> requirement.entityMeetsRequirement(moveEntityEvent.getTargetEntity()))) {
                            if (condition instanceof AreaCondition && ((AreaCondition) condition).isInArea(moveEntityEvent.getTargetEntity())) {
                                for (Action action : trigger.getActions()) {
                                    action.perform(moveEntityEvent.getTargetEntity());
                                }
                                break;
                            }
                        }

                    }
                }
            }
        }
    }

    private boolean shouldTriggerRunForPlayer(Trigger trigger, Player player) {
        return Interval.ALWAYS.equals(trigger.getRepeat())
                || playerTriggerActivationService.loadAll().stream()
                .noneMatch(playerTriggerActivation -> playerTriggerActivation.getPlayerId().equals(player.getUniqueId())
                        && playerTriggerActivation.getTriggerId().equals(trigger.getId()));
    }
}

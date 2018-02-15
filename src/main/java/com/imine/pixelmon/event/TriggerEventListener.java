package com.imine.pixelmon.event;

import com.imine.pixelmon.model.PlayerTriggerActivation;
import com.imine.pixelmon.service.PlayerTriggerActivationService;
import com.imine.pixelmon.service.TriggerService;
import com.imine.pixelmon.trigger.Interval;
import com.imine.pixelmon.trigger.Trigger;
import com.imine.pixelmon.trigger.action.Action;
import com.imine.pixelmon.trigger.condition.AreaCondition;
import com.imine.pixelmon.trigger.condition.Condition;
import com.imine.pixelmon.trigger.condition.EntityInteractCondition;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.entity.InteractEntityEvent;
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
            for (Trigger trigger : triggerService.getAll()) {
                if (shouldTriggerRunForPlayer(trigger, player)) {
                    for (Condition condition : trigger.getConditions()) {
                        if (condition instanceof AreaCondition) {
                            if (condition.matchesRequirements(player)) {
                                if (isEnteringTrigger((AreaCondition) condition, moveEntityEvent)) {
                                    for (Action action : trigger.getActions()) {
                                        action.perform(player);
                                        if (trigger.getRepeat().equals(Interval.ONCE)) {
                                            playerTriggerActivationService.add(new PlayerTriggerActivation(player.getUniqueId(), trigger.getId()));
                                        }
                                    }
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @Listener
    public void onEntityInteract(InteractEntityEvent interactEntityEvent) {
        interactEntityEvent.getCause().first(Player.class).ifPresent(player -> {
            triggerService.getAll()
                    .forEach(trigger -> trigger.getConditions().stream()
                            .filter(EntityInteractCondition.class::isInstance)
                            .map(EntityInteractCondition.class::cast)
                            .forEach(condition -> {
                                if (condition.entityMatches(interactEntityEvent.getTargetEntity()) && condition.matchesRequirements(player)) {
                                    trigger.getActions().forEach(action -> action.perform(player));
                                    if (trigger.getRepeat().equals(Interval.ONCE)) {
                                        playerTriggerActivationService.add(new PlayerTriggerActivation(player.getUniqueId(), trigger.getId()));
                                    }
                                }
                            }));
        });
    }

    private boolean shouldTriggerRunForPlayer(Trigger trigger, Player player) {
        return Interval.ALWAYS.equals(trigger.getRepeat())
                || playerTriggerActivationService.loadAll().stream()
                .noneMatch(playerTriggerActivation -> playerTriggerActivation.getPlayerId().equals(player.getUniqueId())
                        && playerTriggerActivation.getTriggerId().equals(trigger.getId()));
    }

    /**
     * Verifies that the Move event was triggered due to an player moving into an area instead of moving inside an area
     *
     * @param areaCondition   The AreaCondition to check the area of
     * @param moveEntityEvent the Event to get the source and target position
     * @return true if the player moves into the area and was not already inside it
     */
    private boolean isEnteringTrigger(AreaCondition areaCondition, MoveEntityEvent moveEntityEvent) {
        return areaCondition.isInArea(moveEntityEvent.getToTransform().getPosition())
                && !areaCondition.isInArea(moveEntityEvent.getFromTransform().getPosition());
    }
}

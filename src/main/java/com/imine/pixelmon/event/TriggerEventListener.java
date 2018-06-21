package com.imine.pixelmon.event;

import com.imine.pixelmon.component.TriggerActivator;
import com.imine.pixelmon.model.PlayerTriggerActivation;
import com.imine.pixelmon.model.TriggerException;
import com.imine.pixelmon.service.PlayerTriggerActivationService;
import com.imine.pixelmon.service.TriggerService;
import com.imine.pixelmon.trigger.Interval;
import com.imine.pixelmon.trigger.Trigger;
import com.imine.pixelmon.trigger.action.Action;
import com.imine.pixelmon.trigger.condition.AreaCondition;
import com.imine.pixelmon.trigger.condition.BlockInteractCondition;
import com.imine.pixelmon.trigger.condition.Condition;
import com.imine.pixelmon.trigger.condition.EntityInteractCondition;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.Order;
import org.spongepowered.api.event.block.InteractBlockEvent;
import org.spongepowered.api.event.entity.InteractEntityEvent;
import org.spongepowered.api.event.entity.MoveEntityEvent;
import org.spongepowered.api.event.filter.cause.Root;
import org.spongepowered.api.event.filter.type.Exclude;

public class TriggerEventListener {

    private final TriggerService triggerService;
    private final PlayerTriggerActivationService playerTriggerActivationService;
    private final TriggerActivator triggerActivator;

    public TriggerEventListener(TriggerService triggerService, PlayerTriggerActivationService playerTriggerActivationService, TriggerActivator triggerActivator) {
        this.triggerService = triggerService;
        this.playerTriggerActivationService = playerTriggerActivationService;
        this.triggerActivator = triggerActivator;
    }

    @Listener(order = Order.LATE)
    @Exclude(MoveEntityEvent.Teleport.class)
    public void onEntityMove(MoveEntityEvent moveEntityEvent, @Root Player player) {
        for (Trigger trigger : triggerService.getAll()) {
            try {
                attemptToRunEntityMoveTrigger(moveEntityEvent, player, trigger);
            } catch (RuntimeException e) {
                throw new TriggerException(trigger, e);
            }
        }
    }

    private void attemptToRunEntityMoveTrigger(MoveEntityEvent moveEntityEvent, Player player, Trigger trigger) {
        if (shouldTriggerRunForPlayer(trigger, player)) {
            for (Condition condition : trigger.getConditions()) {
                if (condition instanceof AreaCondition) {
                    if (condition.matchesRequirements(player)) {
                        if (isEnteringTrigger((AreaCondition) condition, moveEntityEvent)) {
                            triggerActivator.activateTriggerForPlayer(trigger, player);
                            break;
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
                    .stream()
                    .filter(t -> shouldTriggerRunForPlayer(t, player))
                    .forEach(trigger -> trigger.getConditions().stream()
                            .filter(EntityInteractCondition.class::isInstance)
                            .map(EntityInteractCondition.class::cast)
                            .forEach(condition -> {
                                if (condition.entityMatches(interactEntityEvent.getTargetEntity()) && condition.matchesRequirements(player)) {
                                    triggerActivator.activateTriggerForPlayer(trigger, player);
                                }
                            }));
        });
    }

    @Listener
    public void onEntityInteract(InteractBlockEvent.Secondary.MainHand interactBlockEvent, @Root Player player) {
        triggerService.getAll()
                .stream()
                .filter(t -> shouldTriggerRunForPlayer(t, player))
                .forEach(trigger -> trigger.getConditions()
                        .stream()
                        .filter(BlockInteractCondition.class::isInstance)
                        .map(BlockInteractCondition.class::cast)
                        .forEach(condition -> {
                            if (condition.blockMatches(interactBlockEvent.getTargetBlock()) && condition.matchesRequirements(player)) {
                                triggerActivator.activateTriggerForPlayer(trigger, player);
                            }
                        }));
    }

    private boolean shouldTriggerRunForPlayer(Trigger trigger, Player player) {
        return Interval.ALWAYS.equals(trigger.getRepeat())
                || playerTriggerActivationService.getAll().stream()
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

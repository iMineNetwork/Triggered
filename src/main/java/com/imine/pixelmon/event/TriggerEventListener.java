package com.imine.pixelmon.event;

import com.imine.pixelmon.service.TriggerService;
import com.imine.pixelmon.trigger.Trigger;
import com.imine.pixelmon.trigger.action.Action;
import com.imine.pixelmon.trigger.condition.AreaCondition;
import com.imine.pixelmon.trigger.condition.Condition;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.entity.MoveEntityEvent;

public class TriggerEventListener {

    private final TriggerService triggerService;

    public TriggerEventListener(TriggerService triggerService) {
        this.triggerService = triggerService;
    }

    @Listener
    public void onEntityMove(MoveEntityEvent moveEntityEvent) {
        for (Trigger trigger : triggerService.getAll()) {
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

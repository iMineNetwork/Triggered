package com.imine.pixelmon.trigger.action;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.spongepowered.api.entity.living.player.Player;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class DelayedAction implements Action {

    private static final ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();

    private final List<Action> actions;
    private final int delayMs;

    @JsonCreator
    public DelayedAction(@JsonProperty("actions") List<Action> actions, @JsonProperty("delayMs") int delayMs) {
        this.actions = actions;
        this.delayMs = delayMs;
    }

    public List<Action> getActions() {
        return actions;
    }

    public int getDelayMs() {
        return delayMs;
    }

    @Override
    public void perform(Player player) {
        scheduledExecutorService.schedule(() ->  actions.forEach(action -> action.perform(player)), delayMs, TimeUnit.MILLISECONDS);
    }
}

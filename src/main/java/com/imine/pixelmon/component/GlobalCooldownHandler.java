package com.imine.pixelmon.component;

import com.imine.pixelmon.TriggeredPlugin;
import com.imine.pixelmon.trigger.Trigger;
import org.spongepowered.api.scheduler.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class GlobalCooldownHandler {

    private final List<String> triggersInCooldown = new ArrayList<>();

    public boolean isTriggerInCooldown(Trigger trigger) {
        return triggersInCooldown.contains(trigger.getId());
    }

    public void cooldownTrigger(Trigger trigger) {
        trigger.getCooldown().ifPresent(cooldown -> {
                    triggersInCooldown.add(trigger.getId());
                    Task.builder().async().delay(cooldown.getDurationMiliseconds(), TimeUnit.MILLISECONDS).execute(() -> {
                        triggersInCooldown.remove(trigger.getId());
                    }).submit(TriggeredPlugin.getPluginInstance());
                }
        );
    }
}

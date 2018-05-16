package com.imine.pixelmon.component;

import com.flowpowered.math.vector.Vector3d;
import com.imine.pixelmon.service.TriggerService;
import com.imine.pixelmon.trigger.Trigger;
import com.imine.pixelmon.trigger.action.Action;
import com.imine.pixelmon.trigger.action.GiveItemAction;
import com.imine.pixelmon.trigger.condition.Condition;
import com.imine.pixelmon.trigger.condition.IPositioned;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.effect.particle.ParticleEffect;
import org.spongepowered.api.effect.particle.ParticleOption;
import org.spongepowered.api.effect.particle.ParticleOptions;
import org.spongepowered.api.effect.particle.ParticleTypes;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ItemPickupRevealer {

    public static void init(Object plugin, TriggerService triggerService) {
        Sponge.getScheduler().createTaskBuilder()
                .intervalTicks(5)
                .execute(new Revealer(triggerService))
                .submit(plugin);
    }


    private static class Revealer implements Runnable {

        List<Location> locations;

        public Revealer(TriggerService triggerService) {
            this.locations = triggerService.getAll().stream()
                    .filter(trigger -> trigger.getActions().stream().anyMatch(action -> action instanceof GiveItemAction))
                    .flatMap(trigger -> trigger.getConditions().stream())
                    .filter(condition -> condition instanceof IPositioned)
                    .map(IPositioned.class::cast)
                    .map(IPositioned::getLocation)
                    .collect(Collectors.toList());
        }

        @Override
        public void run() {
            locations.forEach(location -> {
                ((World) location.getExtent()).spawnParticles(ParticleEffect.builder()
                        .type(ParticleTypes.ENCHANTING_GLYPHS)
                        .offset(new Vector3d(0.05,0.05,0.05))
                        .velocity(new Vector3d(2,2,2))
                        .quantity(5)
                        .build(), location.getPosition().add(0.5F, 0.75F, 0.5F));
            });
        }
    }
}

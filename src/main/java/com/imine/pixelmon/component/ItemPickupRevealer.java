package com.imine.pixelmon.component;

import com.flowpowered.math.vector.Vector3d;
import com.imine.pixelmon.service.PlayerTriggerActivationService;
import com.imine.pixelmon.service.TriggerService;
import com.imine.pixelmon.trigger.Interval;
import com.imine.pixelmon.trigger.Trigger;
import com.imine.pixelmon.trigger.action.GiveItemAction;
import com.imine.pixelmon.trigger.condition.IPositioned;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.effect.particle.ParticleEffect;
import org.spongepowered.api.effect.particle.ParticleOptions;
import org.spongepowered.api.effect.particle.ParticleTypes;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ItemPickupRevealer {

    public static void init(Object plugin, TriggerService triggerService, PlayerTriggerActivationService playerTriggerActivationService) {
        Sponge.getScheduler().createTaskBuilder()
                .intervalTicks(1)
                .execute(new Revealer(triggerService, playerTriggerActivationService))
                .submit(plugin);
    }


    private static class Revealer implements Runnable {

        private static final Logger LOGGER = LoggerFactory.getLogger(Revealer.class);
        private Map<Trigger, Location> pickupLocations = new HashMap<>();
        private PlayerTriggerActivationService playerTriggerActivationService;

        public Revealer(TriggerService triggerService, PlayerTriggerActivationService playerTriggerActivationService) {
            this.playerTriggerActivationService = playerTriggerActivationService;

            triggerService.getAll()
                    .stream()
                    .filter(trigger -> trigger.getActions().stream().anyMatch(GiveItemAction.class::isInstance))
                    .peek(trigger -> LOGGER.info("Registering Trigger<{}> which has an item somewhere in the map.", trigger.getId()))
                    .forEach(trigger -> trigger.getConditions()
                            .stream()
                            .filter(IPositioned.class::isInstance)
                            .map(IPositioned.class::cast)
                            .findFirst()
                            .map(IPositioned::getLocation)
                            .ifPresent(location -> pickupLocations.put(trigger, location))
                    );
        }

        @Override
        public void run() {

            Sponge.getServer().getOnlinePlayers()
                    .forEach(player -> pickupLocations.keySet().stream()
                            .filter(trigger -> shouldShowForPlayer(trigger, player))
                            .forEach(trigger ->
                                    player.spawnParticles(
                                            ParticleEffect.builder()
                                                    .type(ParticleTypes.ENCHANTING_GLYPHS)
                                                    .velocity(new Vector3d(0, 0.25, 0))
                                                    .offset(new Vector3d(0.25, 0.25, 0.25))
                                                    .option(ParticleOptions.OFFSET, new Vector3d(0, 0, 0))
                                                    .quantity(5)
                                                    .build(),
                                            pickupLocations.get(trigger).getPosition()
                                                    .add(0.5F, 0.75F, 0.5F)
                                    )
                            )
                    )
            ;


        }

        private boolean shouldShowForPlayer(Trigger trigger, Player player) {
            return Interval.ALWAYS.equals(trigger.getRepeat())
                    || playerTriggerActivationService.loadAll().stream()
                    .noneMatch(playerTriggerActivation -> playerTriggerActivation.getPlayerId().equals(player.getUniqueId())
                            && playerTriggerActivation.getTriggerId().equals(trigger.getId()));
        }
    }
}

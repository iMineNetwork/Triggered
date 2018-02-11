package com.imine.pixelmon;

import com.imine.pixelmon.event.TriggerEventListener;
import com.imine.pixelmon.service.PlayerTriggerActivationService;
import com.imine.pixelmon.service.TriggerService;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.config.ConfigDir;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.GameReloadEvent;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.event.game.state.GameStoppedServerEvent;
import org.spongepowered.api.event.game.state.GameStoppingServerEvent;
import org.spongepowered.api.plugin.Plugin;

import javax.inject.Inject;
import java.nio.file.Path;

@Plugin(id = "triggered", name = "Triggered", version = "1.0", description = "Create events with Conditions and Actions")
public class TriggeredPlugin {

    private TriggerService triggerService;
    private PlayerTriggerActivationService playerTriggerActivationService;

    @Inject
    @ConfigDir(sharedRoot = false)
    private Path configPath;

    @Listener
    public void onServerStart(GameStartedServerEvent event) {
        startPlugin();
    }

    @Listener
    public void onServerStop(GameStoppingServerEvent event) {
        stopPlugin();
    }

    @Listener
    public void onGameReload(GameReloadEvent gre) {
        stopPlugin();
        startPlugin();
    }

    private void startPlugin(){
        triggerService = new TriggerService(configPath.resolve("triggers.json"));
        triggerService.loadAll();
        playerTriggerActivationService = new PlayerTriggerActivationService(configPath.resolve("trigger_activations.json"));
        playerTriggerActivationService.loadAll();
        Sponge.getGame().getEventManager().registerListeners(this, new TriggerEventListener(triggerService, playerTriggerActivationService));
    }

    private void stopPlugin() {
        Sponge.getEventManager().unregisterPluginListeners(this);
    }
}

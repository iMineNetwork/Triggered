package com.imine.pixelmon;

import com.imine.pixelmon.event.TriggerEventListener;
import com.imine.pixelmon.service.PlayerTriggerActivationService;
import com.imine.pixelmon.service.TriggerService;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.config.ConfigDir;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.plugin.Plugin;

import javax.inject.Inject;
import java.nio.file.Path;

@Plugin(id = "triggered", name = "Triggered", version = "1.0")
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

    public void startPlugin(){
        triggerService = new TriggerService(configPath.resolve("triggers.json"));
        triggerService.loadAll();
        playerTriggerActivationService = new PlayerTriggerActivationService(configPath.resolve("trigger_activations.json"));
        playerTriggerActivationService.loadAll();
        Sponge.getGame().getEventManager().registerListeners(this, new TriggerEventListener(triggerService, playerTriggerActivationService));
    }
}

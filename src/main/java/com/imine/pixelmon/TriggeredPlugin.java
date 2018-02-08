package com.imine.pixelmon;

import com.imine.pixelmon.event.TriggerEventListener;
import com.imine.pixelmon.service.TriggerService;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.plugin.Plugin;

import java.nio.file.Path;

@Plugin(id = "triggered", name = "Triggered", version = "1.0")
public class TriggeredPlugin {

    private final Path configPath;


    public TriggeredPlugin(Path configPath) {
        this.configPath = configPath.resolve("triggers.json");
    }

    @Listener
    public void onServerStart() {
        TriggerService triggerService = new TriggerService(configPath);
        Sponge.getGame().getEventManager().registerListeners(this, new TriggerEventListener(triggerService));


    }
}

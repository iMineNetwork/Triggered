package com.imine.pixelmon.trigger.action;

import org.spongepowered.api.entity.living.player.Player;

import java.util.HashMap;
import java.util.Map;

public abstract class ContextAction implements Action {

    protected Map<String, String> buildContext(Player player) {
        Map<String, String> context = new HashMap<>();
        context.put("player.name", player.getName());
        context.put("player.id", player.getUniqueId().toString());
        context.put("player.loc.x", String.valueOf(player.getLocation().getX()));
        context.put("player.loc.y", String.valueOf(player.getLocation().getY()));
        context.put("player.loc.z", String.valueOf(player.getLocation().getZ()));
        context.put("player.loc.yaw", String.valueOf(player.getLocation().getX()));
        context.put("player.loc.pitch", String.valueOf(player.getRotation().getY()));
        return context;
    }
}

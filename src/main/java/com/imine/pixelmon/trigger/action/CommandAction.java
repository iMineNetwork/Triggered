package com.imine.pixelmon.trigger.action;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.Entity;

public class CommandAction implements Action {

    private String command;

    public CommandAction() {
    }

    public CommandAction(String command) {
        this.command = command;
    }

    public String getCommand() {
        return command;
    }

    @Override
    public void perform(Entity entity) {
        Sponge.getGame().getCommandManager().process(Sponge.getServer().getConsole(), command);
    }

    @Override
    public String toString() {
        return "CommandAction{" +
                "command='" + command + '\'' +
                '}';
    }
}

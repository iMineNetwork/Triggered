package com.imine.pixelmon.trigger.action;

import org.apache.commons.lang3.text.StrSubstitutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.Player;

public class CommandAction extends ContextAction {

    private static final Logger logger = LoggerFactory.getLogger(CommandAction.class);

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
    public void perform(Player player) {
        String preparedCommand = StrSubstitutor.replace(command, buildContext(player));
        logger.info("Running command '{}' for player '{}'", command, player.getName());
        logger.info("Command resolved to '{}'", preparedCommand);
        Sponge.getGame().getCommandManager().process(Sponge.getServer().getConsole(), preparedCommand);
    }

    @Override
    public String toString() {
        return "CommandAction{" +
                "command='" + command + '\'' +
                '}';
    }
}

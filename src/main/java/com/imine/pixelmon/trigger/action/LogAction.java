package com.imine.pixelmon.trigger.action;

import org.apache.commons.lang3.text.StrSubstitutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.living.player.Player;

public class LogAction extends ContextAction {

    private static Logger logger = LoggerFactory.getLogger(LogAction.class);

    private String logMessage;

    public LogAction() {
    }

    public LogAction(String logMessage) {
        this.logMessage = logMessage;
    }

    public String getLogMessage() {
        return logMessage;
    }

    public void setLogMessage(String logMessage) {
        this.logMessage = logMessage;
    }

    @Override
    public void perform(Player player) {
        logger.info(StrSubstitutor.replace(logMessage, buildContext(player)));
    }

    @Override
    public String toString() {
        return "LogAction{" +
                "logMessage='" + logMessage + '\'' +
                '}';
    }
}

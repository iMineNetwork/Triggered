package com.imine.pixelmon.trigger.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.Entity;

public class LogAction implements Action {

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
    public void perform(Entity entity) {
        logger.info(logMessage, entity.get(Keys.DISPLAY_NAME));
    }

    @Override
    public String toString() {
        return "LogAction{" +
                "logMessage='" + logMessage + '\'' +
                '}';
    }
}

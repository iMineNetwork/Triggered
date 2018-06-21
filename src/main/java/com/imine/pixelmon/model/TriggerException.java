package com.imine.pixelmon.model;

import com.imine.pixelmon.trigger.Trigger;

public class TriggerException extends RuntimeException {

    public TriggerException(Trigger trigger, Throwable cause) {
        super("An Exception occured while running trigger '" + trigger.getId() +"'", cause);
    }

}

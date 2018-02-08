package com.imine.pixelmon.service;

import com.imine.pixelmon.trigger.Trigger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;

public class TriggerService extends AbstractJsonService<Trigger> {

    private static final Logger logger = LoggerFactory.getLogger(TriggerService.class);

    public TriggerService(Path triggerStoragePath) {
        super(triggerStoragePath);
    }

}

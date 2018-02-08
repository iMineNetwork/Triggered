package com.imine.pixelmon.service;

import com.imine.pixelmon.model.PlayerTriggerActivation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;

public class PlayerTriggerActivationService extends AbstractJsonService<PlayerTriggerActivation> {

    private static final Logger logger = LoggerFactory.getLogger(PlayerTriggerActivationService.class);

    public PlayerTriggerActivationService(Path playerTriggerActivationStoragePath) {
        super(playerTriggerActivationStoragePath);
    }

}

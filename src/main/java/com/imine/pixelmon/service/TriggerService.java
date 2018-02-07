package com.imine.pixelmon.service;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.flowpowered.math.vector.Vector3d;
import com.imine.pixelmon.json.AABBDeserializer;
import com.imine.pixelmon.json.AABBSerializer;
import com.imine.pixelmon.json.Vector3dDeserializer;
import com.imine.pixelmon.json.Vector3dSerializer;
import com.imine.pixelmon.trigger.Trigger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongepowered.api.util.AABB;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class TriggerService {

    private static final Logger logger = LoggerFactory.getLogger(TriggerService.class);

    private final Path triggerStoragePath;

    private List<Trigger> triggerCache;

    public TriggerService(Path triggerStoragePath) {
        this.triggerStoragePath = triggerStoragePath;
    }

    public List<Trigger> getTriggers() {
        setUpFiles();
        if(triggerCache == null) {
            try {
                triggerCache = createObjectMapper().readValue(Files.newInputStream(triggerStoragePath), new TypeReference<List<Trigger>>() {});
            } catch (IOException e) {
                logger.error("Exception while while reading Triggers from {}. Trigger list will not be initialized to prevent overwriting storage to an empty file ({}: {})", triggerStoragePath.toAbsolutePath().toString(), e.getClass().getSimpleName(), e.getMessage());
            }
        }
        return triggerCache;
    }

    public void saveTriggers(List<Trigger> triggers) throws IOException {
        setUpFiles();
        if(triggerCache != null) {
            if (!triggerStoragePath.toFile().exists()) {
                Files.createFile(triggerStoragePath);
            }
            createObjectMapper().writeValue(Files.newOutputStream(triggerStoragePath), triggers);
        } else {
            logger.error("Trigger Cache was empty, not saving file to prevent possible data loss");
        }
    }

    public boolean setUpFiles() {
        try {
            if (triggerStoragePath.toAbsolutePath().getParent() != null && !Files.exists(triggerStoragePath.toAbsolutePath().getParent())) {
                Files.createDirectories(triggerStoragePath.toAbsolutePath().getParent());
            }

            if (!Files.exists(triggerStoragePath)) {
                Files.createFile(triggerStoragePath);
            }


        } catch (IOException ioe) {
            logger.error("An exception occurred while creating config files ({}: {})", ioe.getClass().getSimpleName(), ioe.getLocalizedMessage());
            return false;
        }
        return true;
    }

    private ObjectMapper createObjectMapper() {
        ObjectMapper ret = new ObjectMapper();
        ret.enable(JsonGenerator.Feature.IGNORE_UNKNOWN);
        ret.enable(JsonParser.Feature.IGNORE_UNDEFINED);
        ret.enable(SerializationFeature.INDENT_OUTPUT);
        SimpleModule module = new SimpleModule();
        module.addSerializer(Vector3d.class, new Vector3dSerializer());
        module.addDeserializer(Vector3d.class, new Vector3dDeserializer());
        module.addSerializer(AABB.class, new AABBSerializer());
        module.addDeserializer(AABB.class, new AABBDeserializer());
        ret.registerModule(module);
        ret.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return ret;
    }
}

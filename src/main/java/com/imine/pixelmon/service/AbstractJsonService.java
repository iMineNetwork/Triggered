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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongepowered.api.util.AABB;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class AbstractJsonService<T> {

    private static Logger logger = LoggerFactory.getLogger(AbstractJsonService.class);
    private final Path storagePath;
    private List<T> jsonListCache;
    private ObjectMapper objectMapper;

    public AbstractJsonService(Path storagePath) {
        this.storagePath = storagePath;
    }

    public List<T> getAll() {
        setUpFiles();
        if (jsonListCache == null) {
            try {
                jsonListCache = createObjectMapper().readValue(Files.newInputStream(storagePath), new TypeReference<List<T>>() {
                });
            } catch (IOException e) {
                logger.error("Exception while while reading Json from {}. List will not be initialized to prevent overwriting storage to an empty file ({}: {})", storagePath.toAbsolutePath().toString(), e.getClass().getSimpleName(), e.getMessage());
            }
        }
        return jsonListCache;
    }

    public void saveAll(List<T> triggers) throws IOException {
        setUpFiles();
        if (jsonListCache != null) {
            if (!storagePath.toFile().exists()) {
                Files.createFile(storagePath);
            }
            createObjectMapper().writeValue(Files.newOutputStream(storagePath), triggers);
        } else {
            logger.error("Cache was empty, not saving file to prevent possible data loss");
        }
    }

    protected ObjectMapper createObjectMapper() {
        if (objectMapper == null) {
            objectMapper = new ObjectMapper();
            objectMapper.enable(JsonGenerator.Feature.IGNORE_UNKNOWN);
            objectMapper.enable(JsonParser.Feature.IGNORE_UNDEFINED);
            objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
            SimpleModule module = new SimpleModule();
            module.addSerializer(Vector3d.class, new Vector3dSerializer());
            module.addDeserializer(Vector3d.class, new Vector3dDeserializer());
            module.addSerializer(AABB.class, new AABBSerializer());
            module.addDeserializer(AABB.class, new AABBDeserializer());
            objectMapper.registerModule(module);
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        }
        return objectMapper;
    }

    public boolean setUpFiles() {
        try {
            if (storagePath.toAbsolutePath().getParent() != null && !Files.exists(storagePath.toAbsolutePath().getParent())) {
                Files.createDirectories(storagePath.toAbsolutePath().getParent());
            }

            if (!Files.exists(storagePath)) {
                Files.createFile(storagePath);
                Files.write(storagePath, "[]".getBytes());
            }

        } catch (IOException ioe) {
            logger.error("An exception occurred while creating config files ({}: {})", ioe.getClass().getSimpleName(), ioe.getLocalizedMessage());
            return false;
        }
        return true;
    }
}

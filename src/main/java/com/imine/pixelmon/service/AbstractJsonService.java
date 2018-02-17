package com.imine.pixelmon.service;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.flowpowered.math.vector.Vector3d;
import com.imine.pixelmon.json.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongepowered.api.util.AABB;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AbstractJsonService<T> {

    private static Logger logger = LoggerFactory.getLogger(AbstractJsonService.class);
    private final Path storagePath;
    T targetClass;
    private List<T> jsonListCache;
    private ObjectMapper objectMapper;

    public AbstractJsonService(Path storagePath) {
        this.storagePath = storagePath;
    }

    public List<T> getAll() {
        if (jsonListCache != null) {
            return jsonListCache;
        } else {
            logger.warn("List was not initialized yet, returning empty");
            return new ArrayList<>();
        }
    }

    public List<T> loadAll() {
        setUpFiles();
        try {
            Class clazz = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
            if (!Files.isDirectory(storagePath)) {
                jsonListCache = createObjectMapper().readValue(Files.newInputStream(storagePath), objectMapper.getTypeFactory().constructCollectionType(List.class, clazz));
            } else {
                jsonListCache = new ArrayList<>();
                Files.walk(storagePath).filter(path -> !Files.isDirectory(path)).forEach(path -> {
                    try {
                        jsonListCache.addAll(createObjectMapper().readValue(Files.newInputStream(path), objectMapper.getTypeFactory().constructCollectionType(List.class, clazz)));
                    } catch (IOException e) {
                        logger.warn("Failed to load file ''. Reason: ({} : {})", path.toAbsolutePath().toString(), e.getClass().getSimpleName(), e.getMessage());
                        e.printStackTrace();
                    }
                });
            }
        } catch (IOException e) {
            logger.error("Exception while while reading Json from {}. List will not be initialized to prevent overwriting storage to an empty file ({}: {})", storagePath.toAbsolutePath().toString(), e.getClass().getSimpleName(), e.getMessage());
        }
        return jsonListCache;
    }

    public void add(T object) {
        jsonListCache.add(object);
        saveAll();
    }

    private void saveAll() {
        setUpFiles();
        Path savePath = Files.isDirectory(storagePath) ? storagePath.resolve("save.json") : storagePath;
        if (jsonListCache != null) {
            try {
                if (!savePath.toFile().exists()) {
                    Files.createFile(savePath);
                }
                createObjectMapper().writeValue(Files.newOutputStream(savePath), jsonListCache);
            } catch (IOException e) {
                logger.error("Exception while while saving Json to {} ({}: {})", savePath.toAbsolutePath().toString(), e.getClass().getSimpleName(), e.getMessage());
            }
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
            module.addSerializer(UUID.class, new UUIDSerializer());
            module.addDeserializer(UUID.class, new UUIDDeserializer());
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
                if (Files.isDirectory(storagePath)) {
                    Files.createDirectory(storagePath);
                } else {
                    Files.createFile(storagePath);
                    Files.write(storagePath, "[]".getBytes());
                }
            }

        } catch (IOException ioe) {
            logger.error("An exception occurred while creating config files ({}: {})", ioe.getClass().getSimpleName(), ioe.getLocalizedMessage());
            return false;
        }
        return true;
    }
}

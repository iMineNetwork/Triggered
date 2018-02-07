package com.imine.pixelmon.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.flowpowered.math.vector.Vector3d;
import org.spongepowered.api.util.AABB;

import java.io.IOException;

public class AABBSerializer extends JsonSerializer<AABB> {

    @Override
    public void serialize(AABB value, JsonGenerator generator, SerializerProvider serializers) throws IOException, JsonProcessingException {
        generator.writeStartObject();
        generator.writeObjectField("min", value.getMin());
        generator.writeObjectField("max", value.getMax());
        generator.writeEndObject();
    }

}

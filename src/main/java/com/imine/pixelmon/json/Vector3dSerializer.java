package com.imine.pixelmon.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.flowpowered.math.vector.Vector3d;

import java.io.IOException;

public class Vector3dSerializer extends JsonSerializer<Vector3d> {

    @Override
    public void serialize(Vector3d value, JsonGenerator generator, SerializerProvider serializers) throws IOException, JsonProcessingException {
        generator.writeStartObject();
        generator.writeNumberField("x", value.getX());
        generator.writeNumberField("y", value.getY());
        generator.writeNumberField("z", value.getZ());
        generator.writeEndObject();
    }

}

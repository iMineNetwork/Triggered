package com.imine.pixelmon.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import com.flowpowered.math.vector.Vector3d;

import java.io.IOException;

public class Vector3dDeserializer extends JsonDeserializer<Vector3d> {

    @Override
    public Vector3d deserialize(JsonParser parser, DeserializationContext context) throws IOException, JsonProcessingException {
        JsonNode node = parser.getCodec().readTree(parser);
        return new Vector3d(node.get("x").asDouble(), node.get("y").asDouble(), node.get("z").asDouble());
    }
}

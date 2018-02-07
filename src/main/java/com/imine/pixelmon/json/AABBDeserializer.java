package com.imine.pixelmon.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.flowpowered.math.vector.Vector3d;
import org.spongepowered.api.util.AABB;

import java.io.IOException;

public class AABBDeserializer extends JsonDeserializer<AABB> {

    @Override
    public AABB deserialize(JsonParser parser, DeserializationContext context) throws IOException, JsonProcessingException {
        JsonNode node = parser.getCodec().readTree(parser);
        Vector3d min = parser.getCodec().treeToValue(node.get("min"), Vector3d.class);
        Vector3d max = parser.getCodec().treeToValue(node.get("max"), Vector3d.class);
        return new AABB(min, max);
    }
}

package com.imine.pixelmon.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.pixelmonmod.pixelmon.api.pokemon.PokemonSpec;

import java.io.IOException;

public class PokemonSpecDeserializer extends JsonDeserializer<PokemonSpec> {

    @Override
    public PokemonSpec deserialize(JsonParser parser, DeserializationContext context) throws IOException {
        return PokemonSpec.from(parser.getText().split(" "));
    }
}

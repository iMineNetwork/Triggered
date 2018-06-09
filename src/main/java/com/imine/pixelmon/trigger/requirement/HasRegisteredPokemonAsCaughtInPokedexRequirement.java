package com.imine.pixelmon.trigger.requirement;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.imine.pixelmon.integration.PixelmonIntegration;
import org.spongepowered.api.entity.living.player.Player;

public class HasRegisteredPokemonAsCaughtInPokedexRequirement extends AbstractPixelmonRegisteredAsCaughtRequirement {

    @JsonCreator
    public HasRegisteredPokemonAsCaughtInPokedexRequirement(@JsonProperty("pokemonID") int id) {
        super(id);
    }

    @Override
    public boolean meetsRequirement(Player player) {
        return PixelmonIntegration.hasPlayerRegisteredPixelmonAsCaughtInPokedex(player, getPixelmonID());
    }

}

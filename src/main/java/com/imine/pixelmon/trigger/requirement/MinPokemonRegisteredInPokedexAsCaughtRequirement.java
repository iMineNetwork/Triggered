package com.imine.pixelmon.trigger.requirement;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.imine.pixelmon.integration.PixelmonIntegration;
import org.slf4j.LoggerFactory;
import org.spongepowered.api.entity.living.player.Player;

public class MinPokemonRegisteredInPokedexAsCaughtRequirement extends AbstractPixelmonRegisteredInPokedexAsCaughtRequirement {

    @JsonCreator
    public MinPokemonRegisteredInPokedexAsCaughtRequirement(@JsonProperty("count") int count) {
        super(count);
    }

    @Override
    public boolean meetsRequirement(Player player) {
        int pixelmonRegisteredInPokedexAsCaught = PixelmonIntegration.getPixelmonRegisteredInPokedexAsCaught(player);
        return pixelmonRegisteredInPokedexAsCaught >= getRequiredCaughtPixelmonCount();
    }
}

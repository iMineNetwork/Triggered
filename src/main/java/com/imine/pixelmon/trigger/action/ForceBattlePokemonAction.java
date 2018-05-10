package com.imine.pixelmon.trigger.action;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.imine.pixelmon.model.PokemonTemplate;
import com.pixelmonmod.pixelmon.battles.controller.participants.PlayerParticipant;
import com.pixelmonmod.pixelmon.battles.controller.participants.WildPixelmonParticipant;
import com.pixelmonmod.pixelmon.entities.pixelmon.EntityPixelmon;
import com.pixelmonmod.pixelmon.storage.PixelmonStorage;
import net.minecraft.entity.player.EntityPlayerMP;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongepowered.api.entity.living.player.Player;

import java.util.Optional;

public class ForceBattlePokemonAction implements Action {

    private static final Logger logger = LoggerFactory.getLogger(ForceBattlePokemonAction.class);

    private final PokemonTemplate pokemonTemplate;

    @JsonCreator
    public ForceBattlePokemonAction(@JsonProperty("pokemonTemplate") PokemonTemplate pokemonTemplate) {
        this.pokemonTemplate = pokemonTemplate;
    }

    @Override
    public void perform(Player player) {
        EntityPixelmon foePixelmon = pokemonTemplate.create(player.getWorld());
        getPlayerFirstPixelmon(player).ifPresent(firstAblePixelmon -> {
            foePixelmon.StartBattle(new PlayerParticipant((EntityPlayerMP) player, firstAblePixelmon), new WildPixelmonParticipant(foePixelmon));
        });
    }

    private Optional<EntityPixelmon> getPlayerFirstPixelmon(Player player) {
        return PixelmonStorage.pokeBallManager.getPlayerStorage((EntityPlayerMP) player).map(playerStorage -> playerStorage.getFirstAblePokemon((net.minecraft.world.World) player.getWorld()));
    }
}

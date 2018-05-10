package com.imine.pixelmon.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.pixelmonmod.pixelmon.api.pokemon.PokemonSpec;
import com.pixelmonmod.pixelmon.battles.attacks.Attack;
import com.pixelmonmod.pixelmon.entities.pixelmon.EntityPixelmon;
import com.pixelmonmod.pixelmon.entities.pixelmon.stats.Moveset;
import com.pixelmonmod.pixelmon.entities.pixelmon.stats.Stats;
import org.spongepowered.api.world.World;

import java.util.List;
import java.util.Optional;

public class PokemonTemplate {

//    private final String name;
//    private final Integer level;
//    private final Integer gender;
//    private final Integer growth;
//    private final Integer nature;
//    private final String ability;
//    private final Integer boss;
//    private final Boolean shiny;
//    private final Integer form;
//    private final Integer ball;

    private final PokemonSpec pokemonSpec;
    private final List<Attack> attacks;
    private final Stats stats;


    @JsonCreator
    public PokemonTemplate(@JsonProperty("pokemonSpec") PokemonSpec pokemonSpec, @JsonProperty("attacks") List<Attack> attacks, @JsonProperty("stats") Stats stats) {
        this.pokemonSpec = pokemonSpec;
        this.attacks = attacks;
        this.stats = stats;
    }

    public Optional<PokemonSpec> getPokemonSpec() {
        return Optional.ofNullable(pokemonSpec);
    }

    public List<Attack> getAttacks() {
        return attacks;
    }

    public Optional<Stats> getStats() {
        return Optional.ofNullable(stats);
    }

    public EntityPixelmon create(World world) {
        EntityPixelmon entityPixelmon = pokemonSpec.create((net.minecraft.world.World) world);
        if(stats != null) {
            entityPixelmon.stats = stats;
        }
        if(!attacks.isEmpty()) {
            Moveset moveset = entityPixelmon.getMoveset();
            moveset.clear();
            moveset.addAll(attacks);
            entityPixelmon.setMoveset(moveset);
        }
        return entityPixelmon;
    }
}

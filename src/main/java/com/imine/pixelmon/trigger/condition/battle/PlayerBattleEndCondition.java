package com.imine.pixelmon.trigger.condition.battle;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.imine.pixelmon.trigger.condition.Condition;
import com.imine.pixelmon.trigger.requirement.Requirement;
import com.pixelmonmod.pixelmon.battles.controller.participants.BattleParticipant;
import com.pixelmonmod.pixelmon.enums.battle.BattleResults;
import org.spongepowered.api.entity.living.player.Player;

import java.util.List;
import java.util.Set;

public abstract class PlayerBattleEndCondition extends Condition {

    public static final String NBT_UNSAFE_DATA = "UnsafeData";
    public static final String NBT_SCOREBOARD_TAGS = "Tags";

    protected final String trainerTag;

    @JsonCreator
    public PlayerBattleEndCondition(@JsonProperty("trainerTag") String trainerTag, @JsonProperty("requirements") List<Requirement> requirements) {
        super(requirements);
        this.trainerTag = trainerTag;
    }

    public abstract boolean matchesCondition(Player player, Set<BattleParticipant> battleParticipantSet, BattleResults battleResults);
}
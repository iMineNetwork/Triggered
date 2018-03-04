package com.imine.pixelmon.trigger.condition.battle;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.imine.pixelmon.trigger.requirement.Requirement;
import com.pixelmonmod.pixelmon.battles.controller.participants.BattleParticipant;
import com.pixelmonmod.pixelmon.enums.battle.BattleResults;
import org.spongepowered.api.data.DataQuery;
import org.spongepowered.api.data.DataView;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.living.player.Player;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public class PlayerBattleWinCondition extends PlayerBattleEndCondition {

    @JsonCreator
    public PlayerBattleWinCondition(@JsonProperty("trainerTag") String trainerTag, @JsonProperty("requirements") List<Requirement> requirements) {
        super(trainerTag, requirements);
    }

    @Override
    public boolean matchesCondition(Player player, Set<BattleParticipant> battleParticipantSet, BattleResults battleResults) {
        return battleResults.equals(BattleResults.VICTORY)
                && battleParticipantSet.stream().anyMatch(battleParticipant -> {
            Optional<Object> oDataView = ((Entity)battleParticipant.getEntity()).toContainer().get(DataQuery.of(NBT_UNSAFE_DATA));
            if(oDataView.isPresent()) {
                DataView dataView = (DataView) oDataView.get();
                Optional<List<?>> oTagList = dataView.getList(DataQuery.of(NBT_SCOREBOARD_TAGS));
                if(oTagList.isPresent()) {
                    List<?> tagList = oTagList.get();
                    return tagList.contains(trainerTag);
                }
            }
            return false;
        });
    }
}

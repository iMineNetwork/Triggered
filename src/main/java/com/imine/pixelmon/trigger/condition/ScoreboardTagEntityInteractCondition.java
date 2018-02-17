package com.imine.pixelmon.trigger.condition;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.imine.pixelmon.trigger.requirement.Requirement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongepowered.api.data.DataQuery;
import org.spongepowered.api.data.DataView;
import org.spongepowered.api.entity.Entity;

import java.util.List;
import java.util.Optional;

public class ScoreboardTagEntityInteractCondition extends EntityInteractCondition {

    private static final Logger logger = LoggerFactory.getLogger(ScoreboardTagEntityInteractCondition.class);
    public static final String UNSAFE_DATA = "UnsafeData";

    private final String tag;

    @JsonCreator
    public ScoreboardTagEntityInteractCondition(@JsonProperty("requirements") List<Requirement> requirements, @JsonProperty("tag") String tag) {
        super(requirements);
        this.tag = tag;
    }

    public String getTag() {
        return tag;
    }

    @Override
    public boolean entityMatches(Entity entity) {
        Optional<Object> oDataView = entity.toContainer().get(DataQuery.of(UNSAFE_DATA));
        if(oDataView.isPresent()) {
            DataView dataView = (DataView) oDataView.get();
            Optional<List<?>> oTagList = dataView.getList(DataQuery.of("Tags"));
            if(oTagList.isPresent()) {
                List<?> tagList = oTagList.get();
                return tagList.contains(tag);
            }
        }
        return false;
    }
}

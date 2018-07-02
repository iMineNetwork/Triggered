package com.imine.pixelmon.trigger.action;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.spongepowered.api.entity.living.player.Player;

import java.util.List;
import java.util.Random;

public class RandomAction implements Action {

    private static final Random RANDOM = new Random();
    private List<List<Action>> actions;

    public RandomAction(@JsonProperty("actionPossibilities") List<List<Action>> actions) {
        this.actions = actions;
    }

    @Override
    public void perform(Player player) {
        List<Action> actions = this.actions.get(RANDOM.nextInt(this.actions.size()));
        actions.forEach(action -> action.perform(player));
    }

    @Override
    public String toString() {
        return "RandomAction{" +
                "actions=" + getListListString(actions) +
                '}';
    }

    private String getListListString(List<List<Action>> list){


        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < list.size(); i++) {
            List<Action> sublist = list.get(i);
            stringBuilder.append(getListString(sublist));
            if (i != list.size()) {
                stringBuilder.append(", ");
            }
        }

        return '[' + stringBuilder.toString() + ']';
    }

    private String getListString(List<Action> list) {
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < list.size(); i++) {
            Object o = list.get(i);
            stringBuilder.append(o);
            if (i != list.size()) {
                stringBuilder.append(", ");
            }
        }

        return '[' + stringBuilder.toString() + ']';
    }
}

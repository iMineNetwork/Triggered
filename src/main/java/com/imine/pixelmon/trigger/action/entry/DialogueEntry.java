package com.imine.pixelmon.trigger.action.entry;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class DialogueEntry {

    private final String name;
    private final String message;

    @JsonCreator
    public DialogueEntry(@JsonProperty("name") String name, @JsonProperty("message") String message) {
        this.name = name;
        this.message = message;
    }

    public String getName() {
        return name;
    }

    public String getMessage() {
        return message;
    }
}

package com.imine.pixelmon.trigger;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Optional;

public class Cooldown {

    private final int duration;
    private final String message;

    @JsonCreator
    public Cooldown(@JsonProperty("duration") int duration, @JsonProperty("message") String message) {
        this.duration = duration;
        this.message = message;
    }

    public int getDurationMiliseconds() {
        return duration;
    }

    public Optional<String> getMessage() {
        return Optional.ofNullable(message);
    }
}

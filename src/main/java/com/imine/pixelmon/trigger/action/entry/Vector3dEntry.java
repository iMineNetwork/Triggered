package com.imine.pixelmon.trigger.action.entry;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Vector3dEntry {

    private final int x;
    private final int y;
    private final int z;

    @JsonCreator
    public Vector3dEntry(@JsonProperty("x") int x, @JsonProperty("y") int y, @JsonProperty("z") int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

}

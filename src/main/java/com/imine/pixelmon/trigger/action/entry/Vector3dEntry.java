package com.imine.pixelmon.trigger.action.entry;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.flowpowered.math.vector.Vector3d;

public class Vector3dEntry {

    private final Vector3d position;

    @JsonCreator
    public Vector3dEntry(@JsonProperty("x") int x, @JsonProperty("y") int y, @JsonProperty("z") int z) {
        position = new Vector3d(x,y,z);
    }

    public Vector3d getVector3d() {
        return position;
    }
}

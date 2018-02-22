package com.imine.pixelmon.trigger.action.entry;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.flowpowered.math.vector.Vector3d;
import com.imine.pixelmon.util.ReflectionsHelper;
import org.spongepowered.api.effect.sound.SoundCategories;
import org.spongepowered.api.effect.sound.SoundCategory;
import org.spongepowered.api.effect.sound.SoundType;
import org.spongepowered.api.effect.sound.SoundTypes;

public class SoundEntry {

    private final SoundType soundType;
    private final SoundCategory soundCategory;
    private final Vector3d position;
    private final double volume;
    private final double pitch;
    private final double minimumvolume;


    @JsonCreator
    public SoundEntry(@JsonProperty("soundType") String soundType,
                      @JsonProperty("SoundCategory") String soundCategory,
                      @JsonProperty("position") Vector3dEntry position,
                      @JsonProperty("volume") double volume) {

        this.soundType = (SoundType) ReflectionsHelper.getFieldByName(SoundTypes.class, soundType);
        this.soundCategory = (SoundCategory) ReflectionsHelper.getFieldByName(SoundCategory.class, soundCategory);
        this.position = position.getVector3d();
        this.volume = volume;
        this.pitch = 0;
        this.minimumvolume = 0;
    }

    @JsonCreator
    public SoundEntry(@JsonProperty("soundType") String soundType,
                      @JsonProperty("SoundCategory") String soundCategory,
                      @JsonProperty("position") Vector3dEntry position,
                      @JsonProperty("volume") double volume,
                      @JsonProperty("pitch") double pitch) {

        this.soundType = (SoundType) ReflectionsHelper.getFieldByName(SoundTypes.class, soundType);
        this.soundCategory = (SoundCategory) ReflectionsHelper.getFieldByName(SoundCategory.class, soundCategory);
        this.position = position.getVector3d();
        this.volume = volume;
        this.pitch = pitch;
        this.minimumvolume = 0;
    }

    @JsonCreator
    public SoundEntry(@JsonProperty("soundType") String soundType,
                      @JsonProperty("SoundCategory") String soundCategory,
                      @JsonProperty("position") Vector3dEntry position,
                      @JsonProperty("volume") double volume,
                      @JsonProperty("pitch") double pitch,
                      @JsonProperty("minimumvolume") double minimumvolume) {

        this.soundType = (SoundType) ReflectionsHelper.getFieldByName(SoundTypes.class, soundType);
        this.soundCategory = (SoundCategory) ReflectionsHelper.getFieldByName(SoundCategory.class, soundCategory);
        this.position = position.getVector3d();
        this.volume = volume;
        this.pitch = pitch;
        this.minimumvolume = minimumvolume;
    }

    @JsonCreator
    public SoundEntry(@JsonProperty("soundType") String soundType,
                      @JsonProperty("position") Vector3dEntry position,
                      @JsonProperty("volume") double volume) {

        this.soundType = (SoundType) ReflectionsHelper.getFieldByName(SoundTypes.class, soundType);
        this.soundCategory = SoundCategories.MASTER;
        this.position = position.getVector3d();
        this.volume = volume;
        this.pitch = 0;
        this.minimumvolume = 0;
    }
    @JsonCreator
    public SoundEntry(@JsonProperty("soundType") String soundType,
                      @JsonProperty("position") Vector3dEntry position,
                      @JsonProperty("volume") double volume,
                      @JsonProperty("pitch") double pitch) {

        this.soundType = (SoundType) ReflectionsHelper.getFieldByName(SoundTypes.class, soundType);
        this.soundCategory = SoundCategories.MASTER;
        this.position = position.getVector3d();
        this.volume = volume;
        this.pitch = pitch;
        this.minimumvolume = 0;
    }

    @JsonCreator
    public SoundEntry(@JsonProperty("soundType") String soundType,
                      @JsonProperty("position") Vector3dEntry position,
                      @JsonProperty("volume") double volume,
                      @JsonProperty("pitch") double pitch,
                      @JsonProperty("minimumvolume") double minimumvolume) {

        this.soundType = (SoundType) ReflectionsHelper.getFieldByName(SoundTypes.class, soundType);
        this.soundCategory = SoundCategories.MASTER;
        this.position = position.getVector3d();
        this.volume = volume;
        this.pitch = pitch;
        this.minimumvolume = minimumvolume;
    }

    public SoundType getSoundType() {
        return soundType;
    }

    public SoundCategory getSoundCategory() {
        return soundCategory;
    }

    public Vector3d getPosition() {
        return position;
    }

    public double getVolume() {
        return volume;
    }

    public double getPitch() {
        return pitch;
    }

    public double getMinimumvolume() {
        return minimumvolume;
    }
}

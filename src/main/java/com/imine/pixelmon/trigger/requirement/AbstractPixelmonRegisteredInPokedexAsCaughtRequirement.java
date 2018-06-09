package com.imine.pixelmon.trigger.requirement;

public abstract class AbstractPixelmonRegisteredInPokedexAsCaughtRequirement implements Requirement {

    private final int count;

    public AbstractPixelmonRegisteredInPokedexAsCaughtRequirement(int count) {
        this.count = count;
    }

    public int getRequiredCaughtPixelmonCount() {
        return count;
    }
}

package com.imine.pixelmon.trigger.requirement;

public abstract class AbstractPixelmonRegisteredAsCaughtRequirement implements Requirement  {
    private int id;

    public AbstractPixelmonRegisteredAsCaughtRequirement(int id) {
        this.id = id;
    }

    public int getPixelmonID() {
        return id;
    }


}

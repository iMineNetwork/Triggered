package com.imine.pixelmon.util;

public class ReflectionsHelper {

    //TODO: test this code, IDK if it works
    public static Object getFieldByName(Class clazz, String name) {
        try {
            return clazz.getClass().getDeclaredField(name).get(clazz);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            throw new IllegalArgumentException(name + " does not exist in " + clazz.getSimpleName());
        }
    }
}

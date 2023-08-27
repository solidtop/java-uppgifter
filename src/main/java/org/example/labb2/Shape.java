package org.example.labb2;

import java.util.Arrays;

public abstract class Shape implements Comparable<Shape> {

    public abstract double getArea();
    public abstract double getPerimeter();
    public static Shape newShape(Class<? extends Shape> shapeClass, Object... args) {
        try {
            Class<?>[] paramTypes = new Class<?>[args.length];
            Arrays.fill(paramTypes, double.class);
            return shapeClass.getConstructor(paramTypes).newInstance(args);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

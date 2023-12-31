package org.example.java1.labb2;

import java.util.Arrays;

public abstract class Shape implements Comparable<Shape> {

    public abstract double getArea();

    public abstract double getPerimeter();

    @Override
    public int compareTo(Shape o) {
        return Double.compare(getArea(), o.getArea());
    }

    public static Shape create(Class<? extends Shape> shapeClass, Object... args) {
        try {
            Class<?>[] paramTypes = new Class<?>[args.length];
            Arrays.fill(paramTypes, float.class);
            return shapeClass.getConstructor(paramTypes).newInstance(args);
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to create shape", e);
        }
    }

    public static Circle createCircle(float radius) {
        return new Circle(radius);
    }

    public static Rectangle createRect(float width, float height) {
        return new Rectangle(width, height);
    }
}

package org.example.labb2;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class App {
    public static void main(String[] args) {

        // Task 1
        List<Shape> shapeList = new ArrayList<>();
        shapeList.add(new Rectangle(10, 10));
        shapeList.add(new Circle(10));

        System.out.println("Unsorted: " + shapeList);
        shapeList.sort(null);
        System.out.println("Sorted: " + shapeList);

        // Task 2
        Set<Shape> shapeSet = new HashSet<>();
        shapeSet.add(new Rectangle(13, 13));
        shapeSet.add(new Rectangle(13, 13));
        shapeSet.add(new Circle(26));
        shapeSet.add(new Circle(26));
        System.out.println(shapeSet);

        // Create shapes using reflection
        Shape rect = Shape.newShape(Rectangle.class, 10, 10);
        Shape circle = Shape.newShape(Circle.class, 5);
    }
}

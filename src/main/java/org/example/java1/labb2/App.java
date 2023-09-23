package org.example.java1.labb2;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class App {
    public static void main(String[] args) {

        // Task 1
        List<Shape> shapeList = new ArrayList<>();
        shapeList.add(new Rectangle(10, 10));
        shapeList.add(new Rectangle(2, 4));
        shapeList.add(new Rectangle(50, 8));
        shapeList.add(new Circle(10));
        shapeList.add(new Circle(3));
        shapeList.add(new Circle(1.5f));

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

        // Create shapes using reflection (a pretty bad solution IMO)
        Shape rect1 = Shape.create(Rectangle.class, 10, 10);
        Shape circle1 = Shape.create(Circle.class, 5);

        // Create shapes using methods for each class that extends Shape
        Rectangle rect2 = Shape.createRect(2.5f, 3.5f);
        Circle circle2 = Shape.createCircle(10);
    }
}

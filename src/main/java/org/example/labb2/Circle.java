package org.example.labb2;

import java.util.Objects;

public class Circle extends Shape {
    private double diameter;

    public Circle(double diameter) {
        this.diameter = diameter;
    }

    public double getDiameter() {
        return diameter;
    }

    public void setDiameter(double diameter) {
        this.diameter = diameter;
    }

    public double getRadius() {
        return diameter / 2;
    }

    @Override
    public double getArea() {
        double radius = getRadius();
        return Math.PI * radius * radius;
    }

    @Override
    public double getPerimeter() {
        return Math.PI * diameter;
    }

    @Override
    public int compareTo(Shape o) {
        return Double.compare(this.getArea(), o.getArea());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Circle circle = (Circle) o;
        return Double.compare(diameter, circle.diameter) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(diameter);
    }

    @Override
    public String toString() {
        return "Circle{" +
                "diameter=" + diameter +
                ", area=" + getArea() +
                '}';
    }
}

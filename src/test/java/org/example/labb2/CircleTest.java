package org.example.labb2;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CircleTest {

    @Test
    void createNewCircleObjectThatStoresRadius() {
        Circle circle = new Circle(5.0f);

        assertEquals(5.0f, circle.getRadius());
    }

    @Test
    void circelObjectShouldReturnCorrectArea() {
        Circle circle = new Circle(5.0f);

        assertEquals(78.53981633974483, circle.getArea());
    }
}
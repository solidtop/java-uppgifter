package org.example.java2.labb1;

public record Product(String name, double price, long quantity) {
    public double totalPrice() {
        return quantity * price;
    }
}

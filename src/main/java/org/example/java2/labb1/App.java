package org.example.java2.labb1;

import org.example.java2.labb1.discounts.*;

public class App {
    public static void main(String[] args) {
        Order order = new Order(new Customer("John Doe"));
        Discount discountChain = new FridayDiscount(new MilkDiscount(new QuantityDiscount(new NoDiscount())));

        Product milk = new Product("Milk", 20.0, 2);
        Product bread = new Product("Bread", 10.0, 2);
        Product carrot = new Product("Carrot", 20.0, 5);

        printDiscountOnProduct(discountChain, milk, order);
        printDiscountOnProduct(discountChain, bread, order);
        printDiscountOnProduct(discountChain, carrot, order);
    }

    static void printDiscountOnProduct(Discount discountChain, Product product, Order order) {
        double discountedPrice = product.totalPrice() - discountChain.apply(product, order);
        String desc = discountChain.getDescription(product, order);
        System.out.println(product.quantity() + "x " + product.name());
        System.out.println("-------------");
        System.out.println("Price: " + product.price() + " SEK");
        System.out.println("Total Price: " + product.totalPrice() + " SEK");
        System.out.print("Applied Discounts: ");
        if (!desc.isEmpty()) {
            System.out.println(desc);
            System.out.println("Discounted Total Price: " + discountedPrice + " SEK");
        } else {
            System.out.println("None");
        }

        System.out.println();
    }
}

package org.example.java2.labb1.discounts;

import org.example.java2.labb1.Order;
import org.example.java2.labb1.Product;

public class NoDiscount implements Discount {

    @Override
    public double apply(Product product, Order customer) {
        return 0;
    }

    @Override
    public String getDescription(Product product, Order customer) {
        return "";
    }
}

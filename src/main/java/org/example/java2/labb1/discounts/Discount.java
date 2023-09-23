package org.example.java2.labb1.discounts;

import org.example.java2.labb1.Order;
import org.example.java2.labb1.Product;

public interface Discount {
    double apply(Product product, Order customer);

    String getDescription(Product product, Order customer);
}

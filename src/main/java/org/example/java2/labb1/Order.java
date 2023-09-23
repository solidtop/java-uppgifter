package org.example.java2.labb1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Order {
    private final Customer customer;
    private final List<Product> products;

    public Order(Customer customer) {
        this.customer = customer;
        products = new ArrayList<>();
    }

    public Customer getCustomer() {
        return customer;
    }

    public List<Product> getProducts() {
        return Collections.unmodifiableList(products);
    }
}

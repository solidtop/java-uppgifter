package org.example.java2.labb1.discounts;

import org.example.java2.labb1.Order;
import org.example.java2.labb1.Product;

public class MilkDiscount extends BaseDiscount {
    public MilkDiscount(Discount nextDiscount) {
        super(nextDiscount);
    }

    @Override
    protected boolean isApplicable(Product product, Order order) {
        return product.name().equalsIgnoreCase("milk");
    }

    @Override
    protected double calculateDiscount(Product product) {
        return product.totalPrice() * 0.05;
    }

    @Override
    protected String getDiscountDescription() {
        return "5% off on milk";
    }
}

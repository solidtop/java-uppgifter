package org.example.java2.labb1.discounts;

import org.example.java2.labb1.Order;
import org.example.java2.labb1.Product;

public class QuantityDiscount extends BaseDiscount {
    public QuantityDiscount(Discount nextDiscount) {
        super(nextDiscount);
    }

    @Override
    protected boolean isApplicable(Product product, Order order) {
        return product.quantity() >= 5;
    }

    @Override
    protected double calculateDiscount(Product product) {
        return product.quantity() * 10;
    }

    @Override
    protected String getDiscountDescription() {
        return "10 SEK off per product for 5+ items";
    }
}

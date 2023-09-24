package org.example.java2.labb1.discounts;

import org.example.java2.labb1.Order;
import org.example.java2.labb1.Product;

import java.util.function.BiPredicate;
import java.util.function.ToDoubleFunction;

public class GeneralDiscount extends BaseDiscount {
    private final String description;
    private final BiPredicate<Product, Order> applicable;
    private final ToDoubleFunction<Product> discount;

    public GeneralDiscount(BiPredicate<Product, Order> applicable, ToDoubleFunction<Product> discount, String description, Discount nextDiscount) {
        super(nextDiscount);
        this.applicable = applicable;
        this.discount = discount;
        this.description = description;
    }

    @Override
    protected boolean isApplicable(Product product, Order order) {
        return applicable.test(product, order);
    }

    @Override
    protected double calculateDiscount(Product product) {
        return discount.applyAsDouble(product);
    }

    @Override
    protected String getDiscountDescription() {
        return description;
    }
}

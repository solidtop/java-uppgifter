package org.example.java2.labb1.discounts;

import org.example.java2.labb1.Order;
import org.example.java2.labb1.Product;

import java.time.DayOfWeek;
import java.time.LocalDate;

public class FridayDiscount extends BaseDiscount {
    public FridayDiscount(Discount nextDiscount) {
        super(nextDiscount);
    }

    @Override
    protected boolean isApplicable(Product product, Order order) {
        return LocalDate.now().getDayOfWeek() == DayOfWeek.FRIDAY;
    }

    @Override
    protected double calculateDiscount(Product product) {
        return product.totalPrice() * 0.1;
    }

    @Override
    protected String getDiscountDescription() {
        return "10% off on Fridays";
    }
}

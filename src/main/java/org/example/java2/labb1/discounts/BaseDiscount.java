package org.example.java2.labb1.discounts;

import org.example.java2.labb1.Order;
import org.example.java2.labb1.Product;

public abstract class BaseDiscount implements Discount {
    protected final Discount nextDiscount;

    public BaseDiscount(Discount nextDiscount) {
        this.nextDiscount = nextDiscount;
    }

    protected abstract boolean isApplicable(Product product, Order order);

    protected abstract double calculateDiscount(Product product);

    protected abstract String getDiscountDescription();


    @Override
    public double apply(Product product, Order order) {
        double discount = 0;
        if (isApplicable(product, order)) {
            discount = calculateDiscount(product);
        }

        discount += nextDiscount.apply(product, order);
        return discount;
    }

    @Override
    public String getDescription(Product product, Order order) {
        if (!isApplicable(product, order)) {
            return nextDiscount.getDescription(product, order);
        }

        String desc = getDiscountDescription();
        String nextDesc = nextDiscount.getDescription(product, order);
        if (!nextDesc.isEmpty()) {
            desc += ", " + nextDesc;
        }
        return desc;
    }
}

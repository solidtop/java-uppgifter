package org.example.java2.labb1.discounts;

import org.example.java2.labb1.Customer;
import org.example.java2.labb1.Order;
import org.example.java2.labb1.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.function.BiPredicate;
import java.util.function.ToDoubleFunction;

import static org.junit.jupiter.api.Assertions.*;

class GeneralDiscountTest {
    private Order mockOrder;
    private GeneralDiscount generalDiscount;

    @BeforeEach
    void setUp() {
        mockOrder = new Order(new Customer("John Doe"));
        BiPredicate<Product, Order> applicable = (product, order) -> product.name().equalsIgnoreCase("lemon");
        ToDoubleFunction<Product> discount = product -> product.price() * 0.1;
        generalDiscount = new GeneralDiscount(applicable, discount, "Test Discount", new NoDiscount());
    }

    @Test
    void Should_ReturnTrue_When_ProductIsApplicable() {
        Product product = new Product("Lemon", 30.0, 1);

        assertTrue(generalDiscount.isApplicable(product, mockOrder));
    }

    @Test
    void Should_ReturnFalse_When_ProductIsNotApplicable() {
        Product product = new Product("Apple", 30.0, 1);

        assertFalse(generalDiscount.isApplicable(product, mockOrder));
    }

    @Test
    void Should_CalculateCorrectDiscount_When_ProductIsApplicable() {
        Product product = new Product("Product", 100.0, 1);

        double calculatedDiscount = generalDiscount.calculateDiscount(product);

        assertEquals(10, calculatedDiscount, 0.001);
    }

    @Test
    void Should_ReturnDiscountDescription_IfProductIsApplicable() {
        Product product = new Product("Lemon", 30.0, 1);

        String description = generalDiscount.getDescription(product, mockOrder);

        assertEquals("Test Discount", description);
    }

    @Test
    void Should_ReturnEmptyDescription_IfProductIsNotApplicable() {
        Product product = new Product("Apple", 30.0, 1);

        String description = generalDiscount.getDescription(product, mockOrder);

        assertTrue(description.isEmpty());
    }

    @Test
    void Should_ApplyChainedDiscountsCorrectly_IfProductIsApplicable() {
        ToDoubleFunction<Product> discount = product -> product.price() * 0.1;
        Discount discountChain =
                new GeneralDiscount((product, order) -> product.price() > 5, discount, "discount1",
                        new GeneralDiscount((product, order) -> product.price() < 20, discount, "discount2",
                                new GeneralDiscount((product, order) -> product.price() > 10, discount, "discount3",
                                        new NoDiscount())));
        Product product = new Product("Product", 10, 1);

        double discountedPrice = product.totalCost() - discountChain.apply(product, mockOrder);

        assertEquals(8, discountedPrice);
    }
}
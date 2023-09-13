package org.example.labb3.service;

import org.example.labb3.entities.Product;
import org.example.labb3.entities.ProductCategory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class WarehouseTest {
    private final Clock fixedClock = Clock.fixed(Instant.now(), ZoneId.systemDefault());
    private LocalDateTime now;

    @BeforeEach
    void setUp() {
        now = LocalDateTime.now(fixedClock);
    }

    @Test
    void Should_AddNewProduct() {
        Warehouse warehouse = new Warehouse(fixedClock);

        Product product = warehouse.addNewProduct("TestProduct", ProductCategory.BOOKS, 5);

        assertNotNull(product);
        assertEquals("TestProduct", product.name());
        assertEquals(ProductCategory.BOOKS, product.category());
        assertEquals(5, product.rating());
        assertNotNull(product.id());
        assertEquals(now, product.createdAt());
        assertEquals(now, product.updatedAt());
    }

    @Test
    void Should_ThrowException_IfNameIsEmpty() {
        Warehouse warehouse = new Warehouse();

        assertThrows(RuntimeException.class, () ->
                warehouse.addNewProduct("", ProductCategory.BOOKS, 5));
    }

    @Test
    void Should_ThrowException_IfRatingIsInvalid() {
        Warehouse warehouse = new Warehouse();

        assertThrows(RuntimeException.class, () ->
                warehouse.addNewProduct("Product", ProductCategory.BOOKS, -1));
        assertThrows(RuntimeException.class, () ->
                warehouse.addNewProduct("Product", ProductCategory.BOOKS, 11));
    }

    @Test
    void Should_ReturnAllProducts() {
        Warehouse warehouse = new Warehouse(List.of(
                new Product("1", "Product1", ProductCategory.BOOKS, 5, now, now),
                new Product("2", "Product2", ProductCategory.BOOKS, 5, now, now)
        ));

        List<Product> products = warehouse.getAllProducts();

        int expected = 2;
        int actual = products.size();
        assertEquals(expected, actual);
    }

    @Test
    void Should_ReturnEmptyList_When_NoProductsExists() {
        Warehouse warehouse = new Warehouse();

        List<Product> products = warehouse.getAllProducts();

        int expected = 0;
        int actual = products.size();
        assertEquals(expected, actual);
    }

    @Test
    void Should_ReturnProduct_IfExists() {
        Product mockProduct = new Product("1", "Product", ProductCategory.BOOKS, 5, now, now);
        Warehouse warehouse = new Warehouse(List.of(mockProduct));

        Optional<Product> productOptional = warehouse.getProductById(mockProduct.id());

        assertTrue(productOptional.isPresent());
        assertEquals(mockProduct, productOptional.get());
    }

    @Test
    void Should_ReturnEmptyOptional_IfProductNotExists() {
        Warehouse warehouse = new Warehouse();

        Optional<Product> productOptional = warehouse.getProductById("1");

        assertTrue(productOptional.isEmpty());
    }

    @Test
    void Should_UpdateAllProductDetails() {
        LocalDateTime createdAt = now.minusMinutes(1);
        Product mockProduct = new Product("1", "Product", ProductCategory.BOOKS, 2, createdAt, createdAt);
        Warehouse warehouse = new Warehouse(Arrays.asList(mockProduct), fixedClock);

        Product updatedProduct = warehouse.updateProduct(mockProduct.id(), "UpdatedProduct", ProductCategory.VIDEO_GAMES, 5);

        assertEquals("UpdatedProduct", updatedProduct.name());
        assertEquals(ProductCategory.VIDEO_GAMES, updatedProduct.category());
        assertEquals(5, updatedProduct.rating());
        assertTrue(updatedProduct.updatedAt().isAfter(createdAt));
    }

    @Test
    void Should_ThrowException_IfInvalidUpdateProductDetails() {
        Product mockProduct = new Product("1", "Product", ProductCategory.BOOKS, 5, now, now);
        Warehouse warehouse = new Warehouse(Arrays.asList(mockProduct));

        assertThrows(RuntimeException.class, () ->
                warehouse.updateProduct("abc", "UpdatedProduct", ProductCategory.MUSIC, 2));
        assertThrows(RuntimeException.class, () ->
                warehouse.updateProduct(mockProduct.id(), "", ProductCategory.MUSIC, 2));
        assertThrows(RuntimeException.class, () ->
                warehouse.updateProduct(mockProduct.id(), "UpdatedProduct", ProductCategory.MUSIC, -1));
        assertThrows(RuntimeException.class, () ->
                warehouse.updateProduct(mockProduct.id(), "UpdatedProduct", ProductCategory.MUSIC, 11));
    }

    @Test
    void Should_ReturnProductsInCategory() {
        Warehouse warehouse = new Warehouse(List.of(
                new Product("1", "Product1", ProductCategory.BOOKS, 5, now, now),
                new Product("2", "Product2", ProductCategory.MUSIC, 6, now, now),
                new Product("3", "Product3", ProductCategory.BOOKS, 8, now, now)
        ));

        List<Product> booksProducts = warehouse.getProductsByCategory(ProductCategory.BOOKS);

        int expected = 2;
        int actual = booksProducts.size();
        assertEquals(expected, actual);
    }

    @Test
    void Should_ReturnProductsSinceSpecifiedDate() {
        Warehouse warehouse = new Warehouse(List.of(
                new Product("1", "Product1", ProductCategory.BOOKS, 5, now.minusDays(5), now.minusDays(5)),
                new Product("2", "Product2", ProductCategory.MUSIC, 6, now.minusDays(3), now.minusDays(3)),
                new Product("3", "Product3", ProductCategory.BOOKS, 8, now, now)
        ));

        LocalDate specifiedDate = LocalDate.of(2023, 9, 9);
        List<Product> products = warehouse.getProductsSince(specifiedDate);

        int expected = 2;
        int actual = products.size();
        assertEquals(expected, actual);
    }

    @Test
    void Should_ReturnModifiedProducts() {
        Warehouse warehouse = new Warehouse(List.of(
                new Product("1", "Product1", ProductCategory.BOOKS, 8, now, now.plusMinutes(1)),
                new Product("2", "Product2", ProductCategory.BOOKS, 8, now, now),
                new Product("3", "Product3", ProductCategory.BOOKS, 8, now, now.plusHours(1))
        ));

        List<Product> products = warehouse.getModifiedProducts();

        int expected = 2;
        int actual = products.size();
        assertEquals(expected, actual);
    }

    @Test
    void Should_ReturnCategoriesTiedToAtLeast1Product() {
        Warehouse warehouse = new Warehouse(List.of(
                new Product("1", "Product1", ProductCategory.BOOKS, 8, now, now),
                new Product("2", "Product2", ProductCategory.VIDEO_GAMES, 4, now, now),
                new Product("3", "Product3", ProductCategory.BOOKS, 3, now, now)
        ));

        Set<ProductCategory> categories = warehouse.getCategoriesWithProducts();

        int expected = 2;
        int actual = categories.size();
        assertEquals(expected, actual);
    }

    @Test
    void Should_ReturnProductCountInSpecifiedCategory() {
        Warehouse warehouse = new Warehouse(List.of(
                new Product("1", "Product1", ProductCategory.BOOKS, 8, now, now),
                new Product("2", "Product2", ProductCategory.VIDEO_GAMES, 4, now, now),
                new Product("3", "Product3", ProductCategory.BOOKS, 3, now, now)
        ));

        long productCount = warehouse.getProductCountInCategory(ProductCategory.BOOKS);

        int expected = 2;
        assertEquals(expected, productCount);
    }

    @Test
    void Should_ReturnProductsWithMaxRatingThisMonth() {
        Warehouse warehouse = new Warehouse(List.of(
                new Product("1", "Product1", ProductCategory.BOOKS, 10, now.minusMonths(1), now.minusMonths(1)),
                new Product("2", "Product2", ProductCategory.BOOKS, 10, now, now),
                new Product("3", "Product3", ProductCategory.BOOKS, 10, now, now),
                new Product("4", "Product4", ProductCategory.BOOKS, 9, now, now)
        ));

        List<Product> products = warehouse.getTopRatedProductsThisMonth();

        int expected = 2;
        int actual = products.size();
        assertEquals(expected, actual);
    }

    @Test
    void Should_ReturnProductCountByFirstLetter() {
        Warehouse warehouse = new Warehouse(List.of(
                new Product("1", "Abc", ProductCategory.BOOKS, 10, now, now),
                new Product("2", "Bcd", ProductCategory.BOOKS, 10, now, now),
                new Product("3", "Abc", ProductCategory.BOOKS, 10, now, now)
        ));

        Map<Character, Long> productMap = warehouse.getProductCountByFirstLetter();

        int expected = 2;
        int actual = productMap.size();
        assertEquals(expected, actual);
    }
}
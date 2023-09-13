package org.example.labb3.service;

import org.example.labb3.entities.Product;
import org.example.labb3.entities.ProductCategory;
import org.example.labb3.entities.Products;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class Warehouse {
    private final List<Product> products;
    private final Clock clock;

    public Warehouse() {
        products = new ArrayList<>();
        clock = Clock.systemDefaultZone();
    }

    public Warehouse(Clock clock) {
        products = new ArrayList<>();
        this.clock = clock;
    }

    public Warehouse(List<Product> products) {
        this.products = products;
        clock = Clock.systemDefaultZone();
    }

    public Warehouse(List<Product> products, Clock clock) {
        this.products = products;
        this.clock = clock;
    }

    public Product addNewProduct(String name, ProductCategory category, int rating) {
        validateName(name);
        validateRating(rating);

        String id = generateId();
        LocalDateTime createdAt = LocalDateTime.now(clock);
        LocalDateTime updatedAt = LocalDateTime.now(clock);
        Product product = new Product(id, name, category, rating, createdAt, updatedAt);
        products.add(product);
        return product;
    }

    public Product updateProduct(String productId, String name, ProductCategory category, int rating) {
        validateName(name);
        validateRating(rating);

        Product product = getProductByIdThrows(productId);
        LocalDateTime updatedAt = LocalDateTime.now(clock);
        Product updatedProduct = new Product(productId, name, category, rating, product.createdAt(), updatedAt);
        int index = products.indexOf(product);
        products.set(index, updatedProduct);
        return updatedProduct;
    }

    public List<Product> getAllProducts() {
        return Collections.unmodifiableList(products);
    }

    public Optional<Product> getProductById(String productId) {
        return products.stream()
                .filter(product -> product.id().equals(productId))
                .findFirst();
    }

    public List<Product> getProductsByCategory(ProductCategory category) {
        return products.stream()
                .filter(product -> product.category().equals(category))
                .sorted(Comparator.comparing(Product::name))
                .toList();
    }

    public List<Product> getProductsSince(LocalDate fromDate) {
        return products.stream()
                .filter(product -> product.createdAt().isAfter(fromDate.atStartOfDay()))
                .toList();
    }

    public List<Product> getModifiedProducts() {
        return products.stream()
                .filter(product -> product.createdAt().isBefore(product.updatedAt()))
                .toList();
    }

    public Set<ProductCategory> getCategoriesWithProducts() {
        return products.stream()
                .map(Product::category)
                .collect(Collectors.toUnmodifiableSet());
    }

    public int getProductCountInCategory(ProductCategory category) {
        return products.stream()
                .filter(product -> product.category() == category)
                .mapToInt(product -> 1)
                .reduce(0, Integer::sum);
    }

    public List<Product> getTopRatedProductsThisMonth() {
        LocalDate today = LocalDate.now(clock);
        int currentMonth = today.getMonthValue();

        return products.stream()
                .filter(product -> product.createdAt().getMonthValue() >= currentMonth)
                .filter(product -> product.rating() == Products.MAX_RATING)
                .sorted(Comparator.comparing(Product::createdAt))
                .toList();
    }

    public Map<Character, Long> getProductCountByFirstLetter() {
        return products.stream()
                .collect(Collectors.groupingBy(
                        product -> product.name().charAt(0),
                        Collectors.counting()
                ));
    }

    private String generateId() {
        return UUID.randomUUID().toString();
    }

    private void validateName(String name) throws IllegalArgumentException {
        if (name.isEmpty())
            throw new IllegalArgumentException("Name cannot be empty");
    }

    private void validateRating(int rating) throws IllegalArgumentException {
        if (rating < Products.MIN_RATING || rating > Products.MAX_RATING)
            throw new IllegalArgumentException("Rating must be between " + Products.MIN_RATING + " and " + Products.MAX_RATING);
    }

    private Product getProductByIdThrows(String productId) throws NoSuchElementException {
        Optional<Product> productOptional = getProductById(productId);
        return productOptional.orElseThrow(() -> new NoSuchElementException("Product not found"));
    }
}

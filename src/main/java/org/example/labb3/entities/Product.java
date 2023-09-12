package org.example.labb3.entities;

import java.time.LocalDateTime;

public record Product(String id, String name, ProductCategory category, int rating, LocalDateTime createdAt, LocalDateTime updatedAt) {
    public static final int MIN_RATING = 0;
    public static final int MAX_RATING = 10;
}

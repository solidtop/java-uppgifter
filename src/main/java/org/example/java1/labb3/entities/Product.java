package org.example.java1.labb3.entities;

import java.time.LocalDateTime;

public record Product(String id, String name, ProductCategory category, int rating, LocalDateTime createdAt,
                      LocalDateTime updatedAt) {

}

package com.juaracoding.model;

// Anda bisa menggunakan record (Java 16+) untuk lebih ringkas
public record Product(
        String name,
        int quantity,
        double price
) {
    public double getTotal() {
        return quantity * price;
    }
}
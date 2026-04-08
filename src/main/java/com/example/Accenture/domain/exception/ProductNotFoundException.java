package com.example.Accenture.domain.exception;

public class ProductNotFoundException extends BusinessException {
    public ProductNotFoundException(String id) {
        super("Product not found: " + id);
    }
}
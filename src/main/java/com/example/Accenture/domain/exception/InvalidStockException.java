package com.example.Accenture.domain.exception;

public class InvalidStockException extends BusinessException {
    public InvalidStockException() {
        super("Stock must be greater than or equal to 0");
    }
}
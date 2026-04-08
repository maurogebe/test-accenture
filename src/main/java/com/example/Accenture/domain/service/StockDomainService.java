package com.example.Accenture.domain.service;

import com.example.Accenture.domain.exception.InvalidStockException;
import org.springframework.stereotype.Component;

@Component
public class StockDomainService {
    
    public void validate(Integer stock) {
        if (stock == null || stock < 0) {
            throw new InvalidStockException();
        }
    }
}
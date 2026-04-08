package com.example.Accenture.application.mapper;

import com.example.Accenture.application.dto.response.ProductResponse;
import com.example.Accenture.domain.model.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductDtoMapper {
    
    public ProductResponse toResponse(Product product) {
        return new ProductResponse(
            product.id(),
            product.branchId(),
            product.name(),
            product.stock()
        );
    }
}
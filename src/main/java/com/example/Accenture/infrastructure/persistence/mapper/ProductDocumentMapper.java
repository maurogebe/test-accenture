package com.example.Accenture.infrastructure.persistence.mapper;

import com.example.Accenture.domain.model.Product;
import com.example.Accenture.infrastructure.persistence.document.ProductDocument;
import org.springframework.stereotype.Component;

@Component
public class ProductDocumentMapper {
    
    public Product toDomain(ProductDocument document) {
        return Product.builder()
            .id(document.getId())
            .branchId(document.getBranchId())
            .name(document.getName())
            .stock(document.getStock())
            .build();
    }
    
    public ProductDocument toDocument(Product product) {
        return ProductDocument.builder()
            .id(product.id())
            .branchId(product.branchId())
            .name(product.name())
            .stock(product.stock())
            .build();
    }
}
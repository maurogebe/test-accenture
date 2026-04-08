package com.example.Accenture.application.service;

import com.example.Accenture.application.dto.request.CreateProductRequest;
import com.example.Accenture.application.dto.request.UpdateNameRequest;
import com.example.Accenture.application.dto.request.UpdateStockRequest;
import com.example.Accenture.application.dto.response.ProductResponse;
import reactor.core.publisher.Mono;

public interface ProductService {
    Mono<ProductResponse> createProduct(String branchId, CreateProductRequest request);
    Mono<Void> deleteProduct(String branchId, String productId);
    Mono<ProductResponse> updateProductStock(String productId, UpdateStockRequest request);
    Mono<ProductResponse> updateProductName(String productId, UpdateNameRequest request);
}
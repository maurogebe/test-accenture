package com.example.Accenture.application.service.impl;

import com.example.Accenture.application.dto.request.CreateProductRequest;
import com.example.Accenture.application.dto.request.UpdateNameRequest;
import com.example.Accenture.application.dto.request.UpdateStockRequest;
import com.example.Accenture.application.dto.response.ProductResponse;
import com.example.Accenture.application.mapper.ProductDtoMapper;
import com.example.Accenture.application.service.ProductService;
import com.example.Accenture.domain.exception.BranchNotFoundException;
import com.example.Accenture.domain.exception.ProductNotFoundException;
import com.example.Accenture.domain.service.StockDomainService;
import com.example.Accenture.domain.model.Product;
import com.example.Accenture.infrastructure.persistence.mapper.ProductDocumentMapper;
import com.example.Accenture.infrastructure.persistence.repository.BranchRepository;
import com.example.Accenture.infrastructure.persistence.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    
    private final ProductRepository productRepository;
    private final BranchRepository branchRepository;
    private final ProductDocumentMapper productDocumentMapper;
    private final ProductDtoMapper productDtoMapper;
    private final StockDomainService stockDomainService;
    
    @Override
    public Mono<ProductResponse> createProduct(String branchId, CreateProductRequest request) {
        stockDomainService.validate(request.stock());
        
        return branchRepository.findById(branchId)
            .switchIfEmpty(Mono.error(new BranchNotFoundException(branchId)))
            .flatMap(branchDocument -> {
                Product product = Product.builder()
                    .branchId(branchId)
                    .name(request.name())
                    .stock(request.stock())
                    .build();
                
                return productRepository.save(productDocumentMapper.toDocument(product));
            })
            .map(productDocumentMapper::toDomain)
            .map(productDtoMapper::toResponse);
    }
    
    @Override
    public Mono<Void> deleteProduct(String branchId, String productId) {
        return branchRepository.findById(branchId)
            .switchIfEmpty(Mono.error(new BranchNotFoundException(branchId)))
            .then(productRepository.findById(productId)
                .switchIfEmpty(Mono.error(new ProductNotFoundException(productId))))
            .flatMap(productDocument -> {
                if (!productDocument.getBranchId().equals(branchId)) {
                    return Mono.error(new ProductNotFoundException(productId));
                }
                return productRepository.deleteById(productId);
            });
    }
    
    @Override
    public Mono<ProductResponse> updateProductStock(String productId, UpdateStockRequest request) {
        stockDomainService.validate(request.stock());
        
        return productRepository.findById(productId)
            .switchIfEmpty(Mono.error(new ProductNotFoundException(productId)))
            .flatMap(document -> {
                document.setStock(request.stock());
                return productRepository.save(document);
            })
            .map(productDocumentMapper::toDomain)
            .map(productDtoMapper::toResponse);
    }
    
    @Override
    public Mono<ProductResponse> updateProductName(String productId, UpdateNameRequest request) {
        return productRepository.findById(productId)
            .switchIfEmpty(Mono.error(new ProductNotFoundException(productId)))
            .flatMap(document -> {
                document.setName(request.name());
                return productRepository.save(document);
            })
            .map(productDocumentMapper::toDomain)
            .map(productDtoMapper::toResponse);
    }
}
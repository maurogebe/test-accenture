package com.example.Accenture.application.service.impl;

import com.example.Accenture.application.dto.request.CreateProductRequest;
import com.example.Accenture.application.dto.request.UpdateNameRequest;
import com.example.Accenture.application.dto.request.UpdateStockRequest;
import com.example.Accenture.application.dto.response.ProductResponse;
import com.example.Accenture.application.mapper.ProductDtoMapper;
import com.example.Accenture.domain.exception.BranchNotFoundException;
import com.example.Accenture.domain.exception.ProductNotFoundException;
import com.example.Accenture.domain.model.Product;
import com.example.Accenture.domain.service.StockDomainService;
import com.example.Accenture.infrastructure.persistence.document.BranchDocument;
import com.example.Accenture.infrastructure.persistence.document.ProductDocument;
import com.example.Accenture.infrastructure.persistence.mapper.ProductDocumentMapper;
import com.example.Accenture.infrastructure.persistence.repository.BranchRepository;
import com.example.Accenture.infrastructure.persistence.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {
    
    @Mock
    private ProductRepository productRepository;
    @Mock
    private BranchRepository branchRepository;
    @Mock
    private ProductDocumentMapper productDocumentMapper;
    @Mock
    private ProductDtoMapper productDtoMapper;
    @Mock
    private StockDomainService stockDomainService;
    
    @InjectMocks
    private ProductServiceImpl productService;
    
    @Test
    void shouldCreateProductSuccessfully() {
        CreateProductRequest request = new CreateProductRequest("Gel", 20);
        
        BranchDocument branch = BranchDocument.builder()
            .id("b1")
            .franchiseId("f1")
            .name("Branch")
            .build();
        
        ProductDocument documentToSave = ProductDocument.builder()
            .branchId("b1")
            .name("Gel")
            .stock(20)
            .build();
        
        ProductDocument saved = ProductDocument.builder()
            .id("p1")
            .branchId("b1")
            .name("Gel")
            .stock(20)
            .build();
        
        Product domainProduct = Product.builder()
            .id("p1")
            .branchId("b1")
            .name("Gel")
            .stock(20)
            .build();
        
        ProductResponse response = new ProductResponse("p1", "b1", "Gel", 20);
        
        when(branchRepository.findById("b1")).thenReturn(Mono.just(branch));
        when(productDocumentMapper.toDocument(any())).thenReturn(documentToSave);
        when(productRepository.save(any(ProductDocument.class))).thenReturn(Mono.just(saved));
        when(productDocumentMapper.toDomain(saved)).thenReturn(domainProduct);
        when(productDtoMapper.toResponse(domainProduct)).thenReturn(response);
        
        StepVerifier.create(productService.createProduct("b1", request))
            .expectNextMatches(result ->
                result.id().equals("p1") &&
                    result.branchId().equals("b1") &&
                    result.name().equals("Gel") &&
                    result.stock().equals(20))
            .verifyComplete();
        
        verify(stockDomainService).validate(20);
    }
    
    @Test
    void shouldFailCreatingProductWhenBranchNotFound() {
        CreateProductRequest request = new CreateProductRequest("Gel", 20);
        
        when(branchRepository.findById("b1")).thenReturn(Mono.empty());
        
        StepVerifier.create(productService.createProduct("b1", request))
            .expectError(BranchNotFoundException.class)
            .verify();
    }
    
    @Test
    void shouldDeleteProductSuccessfully() {
        BranchDocument branch = BranchDocument.builder().id("b1").name("Branch").franchiseId("f1").build();
        ProductDocument product = ProductDocument.builder().id("p1").branchId("b1").name("Gel").stock(20).build();
        
        when(branchRepository.findById("b1")).thenReturn(Mono.just(branch));
        when(productRepository.findById("p1")).thenReturn(Mono.just(product));
        when(productRepository.deleteById("p1")).thenReturn(Mono.empty());
        
        StepVerifier.create(productService.deleteProduct("b1", "p1"))
            .verifyComplete();
        
        verify(productRepository).deleteById("p1");
    }
    
    @Test
    void shouldFailDeletingProductWhenProductNotFound() {
        BranchDocument branch = BranchDocument.builder().id("b1").name("Branch").franchiseId("f1").build();
        
        when(branchRepository.findById("b1")).thenReturn(Mono.just(branch));
        when(productRepository.findById("p1")).thenReturn(Mono.empty());
        
        StepVerifier.create(productService.deleteProduct("b1", "p1"))
            .expectError(ProductNotFoundException.class)
            .verify();
    }
    
    @Test
    void shouldUpdateProductStockSuccessfully() {
        UpdateStockRequest request = new UpdateStockRequest(99);
        ProductDocument product = ProductDocument.builder().id("p1").branchId("b1").name("Gel").stock(10).build();
        ProductDocument updated = ProductDocument.builder().id("p1").branchId("b1").name("Gel").stock(99).build();
        
        when(productRepository.findById("p1")).thenReturn(Mono.just(product));
        when(productRepository.save(any(ProductDocument.class))).thenReturn(Mono.just(updated));
        when(productDocumentMapper.toDomain(updated))
            .thenReturn(Product.builder().id("p1").branchId("b1").name("Gel").stock(99).build());
        when(productDtoMapper.toResponse(any()))
            .thenReturn(new ProductResponse("p1", "b1", "Gel", 99));
        
        StepVerifier.create(productService.updateProductStock("p1", request))
            .expectNextMatches(result -> result.stock().equals(99))
            .verifyComplete();
        
        verify(stockDomainService).validate(99);
    }
    
    @Test
    void shouldFailUpdatingProductStockWhenProductNotFound() {
        UpdateStockRequest request = new UpdateStockRequest(99);
        
        when(productRepository.findById("p1")).thenReturn(Mono.empty());
        
        StepVerifier.create(productService.updateProductStock("p1", request))
            .expectError(ProductNotFoundException.class)
            .verify();
    }
    
    @Test
    void shouldUpdateProductNameSuccessfully() {
        UpdateNameRequest request = new UpdateNameRequest("New Gel");
        ProductDocument product = ProductDocument.builder().id("p1").branchId("b1").name("Old").stock(10).build();
        ProductDocument updated = ProductDocument.builder().id("p1").branchId("b1").name("New Gel").stock(10).build();
        
        when(productRepository.findById("p1")).thenReturn(Mono.just(product));
        when(productRepository.save(any(ProductDocument.class))).thenReturn(Mono.just(updated));
        when(productDocumentMapper.toDomain(updated))
            .thenReturn(Product.builder().id("p1").branchId("b1").name("New Gel").stock(10).build());
        when(productDtoMapper.toResponse(any()))
            .thenReturn(new ProductResponse("p1", "b1", "New Gel", 10));
        
        StepVerifier.create(productService.updateProductName("p1", request))
            .expectNextMatches(result -> result.name().equals("New Gel"))
            .verifyComplete();
    }
}
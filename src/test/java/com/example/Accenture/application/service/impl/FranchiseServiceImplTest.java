package com.example.Accenture.application.service.impl;

import com.example.Accenture.application.dto.request.CreateFranchiseRequest;
import com.example.Accenture.application.dto.request.UpdateNameRequest;
import com.example.Accenture.application.dto.response.FranchiseResponse;
import com.example.Accenture.application.mapper.FranchiseDtoMapper;
import com.example.Accenture.domain.exception.FranchiseNotFoundException;
import com.example.Accenture.infrastructure.persistence.document.BranchDocument;
import com.example.Accenture.infrastructure.persistence.document.FranchiseDocument;
import com.example.Accenture.infrastructure.persistence.document.ProductDocument;
import com.example.Accenture.infrastructure.persistence.mapper.BranchDocumentMapper;
import com.example.Accenture.infrastructure.persistence.mapper.FranchiseDocumentMapper;
import com.example.Accenture.infrastructure.persistence.mapper.ProductDocumentMapper;
import com.example.Accenture.infrastructure.persistence.repository.BranchRepository;
import com.example.Accenture.infrastructure.persistence.repository.FranchiseRepository;
import com.example.Accenture.infrastructure.persistence.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FranchiseServiceImplTest {
    
    @Mock
    private FranchiseRepository franchiseRepository;
    @Mock
    private BranchRepository branchRepository;
    @Mock
    private ProductRepository productRepository;
    @Mock
    private FranchiseDocumentMapper franchiseDocumentMapper;
    @Mock
    private BranchDocumentMapper branchDocumentMapper;
    @Mock
    private ProductDocumentMapper productDocumentMapper;
    @Mock
    private FranchiseDtoMapper franchiseDtoMapper;
    
    @InjectMocks
    private FranchiseServiceImpl franchiseService;
    
    private FranchiseDocument franchiseDocument;
    
    @BeforeEach
    void setUp() {
        franchiseDocument = FranchiseDocument.builder()
            .id("f1")
            .name("Franchise A")
            .build();
    }
    
    @Test
    void shouldCreateFranchiseSuccessfully() {
        CreateFranchiseRequest request = new CreateFranchiseRequest("Franchise A");
        
        FranchiseDocument documentToSave = FranchiseDocument.builder()
            .name("Franchise A")
            .build();
        
        FranchiseDocument savedDocument = FranchiseDocument.builder()
            .id("f1")
            .name("Franchise A")
            .build();
        
        var domainFranchise = com.example.Accenture.domain.model.Franchise.builder()
            .id("f1")
            .name("Franchise A")
            .build();
        
        FranchiseResponse response = new FranchiseResponse("f1", "Franchise A");
        
        when(franchiseDocumentMapper.toDocument(any())).thenReturn(documentToSave);
        when(franchiseRepository.save(any(FranchiseDocument.class))).thenReturn(Mono.just(savedDocument));
        when(franchiseDocumentMapper.toDomain(savedDocument)).thenReturn(domainFranchise);
        when(franchiseDtoMapper.toResponse(domainFranchise)).thenReturn(response);
        
        StepVerifier.create(franchiseService.createFranchise(request))
            .expectNextMatches(result ->
                result.id().equals("f1") &&
                    result.name().equals("Franchise A"))
            .verifyComplete();
    }
    
    @Test
    void shouldUpdateFranchiseNameSuccessfully() {
        UpdateNameRequest request = new UpdateNameRequest("Updated Franchise");
        FranchiseDocument updated = FranchiseDocument.builder()
            .id("f1")
            .name("Updated Franchise")
            .build();
        
        when(franchiseRepository.findById("f1")).thenReturn(Mono.just(franchiseDocument));
        when(franchiseRepository.save(any(FranchiseDocument.class))).thenReturn(Mono.just(updated));
        when(franchiseDocumentMapper.toDomain(updated))
            .thenReturn(com.example.Accenture.domain.model.Franchise.builder()
                .id("f1")
                .name("Updated Franchise")
                .build());
        when(franchiseDtoMapper.toResponse(any()))
            .thenReturn(new FranchiseResponse("f1", "Updated Franchise"));
        
        StepVerifier.create(franchiseService.updateFranchiseName("f1", request))
            .expectNextMatches(result ->
                result.id().equals("f1") &&
                    result.name().equals("Updated Franchise"))
            .verifyComplete();
    }
    
    @Test
    void shouldFailUpdatingFranchiseNameWhenNotFound() {
        UpdateNameRequest request = new UpdateNameRequest("Updated Franchise");
        
        when(franchiseRepository.findById("f1")).thenReturn(Mono.empty());
        
        StepVerifier.create(franchiseService.updateFranchiseName("f1", request))
            .expectError(FranchiseNotFoundException.class)
            .verify();
    }
    
    @Test
    void shouldReturnTopStockProductsByFranchise() {
        BranchDocument branch = BranchDocument.builder()
            .id("b1")
            .franchiseId("f1")
            .name("Medellin Branch")
            .build();
        
        ProductDocument p1 = ProductDocument.builder()
            .id("p1")
            .branchId("b1")
            .name("Gel")
            .stock(10)
            .build();
        
        ProductDocument p2 = ProductDocument.builder()
            .id("p2")
            .branchId("b1")
            .name("Shampoo")
            .stock(30)
            .build();
        
        when(franchiseRepository.findById("f1")).thenReturn(Mono.just(franchiseDocument));
        when(branchRepository.findByFranchiseId("f1")).thenReturn(Flux.just(branch));
        when(branchDocumentMapper.toDomain(branch))
            .thenReturn(com.example.Accenture.domain.model.Branch.builder()
                .id("b1")
                .franchiseId("f1")
                .name("Medellin Branch")
                .build());
        when(productRepository.findByBranchId("b1")).thenReturn(Flux.just(p1, p2));
        when(productDocumentMapper.toDomain(p1))
            .thenReturn(com.example.Accenture.domain.model.Product.builder()
                .id("p1").branchId("b1").name("Gel").stock(10).build());
        when(productDocumentMapper.toDomain(p2))
            .thenReturn(com.example.Accenture.domain.model.Product.builder()
                .id("p2").branchId("b1").name("Shampoo").stock(30).build());
        
        StepVerifier.create(franchiseService.getTopStockProductsByFranchise("f1"))
            .expectNextMatches(result ->
                result.branchId().equals("b1") &&
                    result.branchName().equals("Medellin Branch") &&
                    result.productId().equals("p2") &&
                    result.productName().equals("Shampoo") &&
                    result.stock().equals(30))
            .verifyComplete();
    }
}
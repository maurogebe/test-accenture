package com.example.Accenture.application.service.impl;

import com.example.Accenture.application.dto.request.CreateBranchRequest;
import com.example.Accenture.application.dto.request.UpdateNameRequest;
import com.example.Accenture.application.dto.response.BranchResponse;
import com.example.Accenture.application.mapper.BranchDtoMapper;
import com.example.Accenture.domain.exception.BranchNotFoundException;
import com.example.Accenture.domain.exception.FranchiseNotFoundException;
import com.example.Accenture.domain.model.Branch;
import com.example.Accenture.infrastructure.persistence.document.BranchDocument;
import com.example.Accenture.infrastructure.persistence.document.FranchiseDocument;
import com.example.Accenture.infrastructure.persistence.mapper.BranchDocumentMapper;
import com.example.Accenture.infrastructure.persistence.repository.BranchRepository;
import com.example.Accenture.infrastructure.persistence.repository.FranchiseRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BranchServiceImplTest {
    
    @Mock
    private BranchRepository branchRepository;
    @Mock
    private FranchiseRepository franchiseRepository;
    @Mock
    private BranchDocumentMapper branchDocumentMapper;
    @Mock
    private BranchDtoMapper branchDtoMapper;
    
    @InjectMocks
    private BranchServiceImpl branchService;
    
    @Test
    void shouldCreateBranchSuccessfully() {
        CreateBranchRequest request = new CreateBranchRequest("Bogota Branch");
        
        FranchiseDocument franchise = FranchiseDocument.builder()
            .id("f1")
            .name("Franchise")
            .build();
        
        BranchDocument documentToSave = BranchDocument.builder()
            .franchiseId("f1")
            .name("Bogota Branch")
            .build();
        
        BranchDocument saved = BranchDocument.builder()
            .id("b1")
            .franchiseId("f1")
            .name("Bogota Branch")
            .build();
        
        Branch domainBranch = Branch.builder()
            .id("b1")
            .franchiseId("f1")
            .name("Bogota Branch")
            .build();
        
        BranchResponse response = new BranchResponse("b1", "f1", "Bogota Branch");
        
        when(franchiseRepository.findById("f1")).thenReturn(Mono.just(franchise));
        when(branchDocumentMapper.toDocument(any())).thenReturn(documentToSave);
        when(branchRepository.save(any(BranchDocument.class))).thenReturn(Mono.just(saved));
        when(branchDocumentMapper.toDomain(saved)).thenReturn(domainBranch);
        when(branchDtoMapper.toResponse(domainBranch)).thenReturn(response);
        
        StepVerifier.create(branchService.createBranch("f1", request))
            .expectNextMatches(result ->
                result.id().equals("b1") &&
                    result.franchiseId().equals("f1") &&
                    result.name().equals("Bogota Branch"))
            .verifyComplete();
    }
    
    @Test
    void shouldFailCreatingBranchWhenFranchiseDoesNotExist() {
        CreateBranchRequest request = new CreateBranchRequest("Bogota Branch");
        
        when(franchiseRepository.findById("f1")).thenReturn(Mono.empty());
        
        StepVerifier.create(branchService.createBranch("f1", request))
            .expectError(FranchiseNotFoundException.class)
            .verify();
    }
    
    @Test
    void shouldUpdateBranchNameSuccessfully() {
        UpdateNameRequest request = new UpdateNameRequest("Updated Branch");
        BranchDocument branch = BranchDocument.builder().id("b1").franchiseId("f1").name("Old").build();
        BranchDocument updated = BranchDocument.builder().id("b1").franchiseId("f1").name("Updated Branch").build();
        
        when(branchRepository.findById("b1")).thenReturn(Mono.just(branch));
        when(branchRepository.save(any(BranchDocument.class))).thenReturn(Mono.just(updated));
        when(branchDocumentMapper.toDomain(updated))
            .thenReturn(Branch.builder().id("b1").franchiseId("f1").name("Updated Branch").build());
        when(branchDtoMapper.toResponse(any()))
            .thenReturn(new BranchResponse("b1", "f1", "Updated Branch"));
        
        StepVerifier.create(branchService.updateBranchName("b1", request))
            .expectNextMatches(result -> result.name().equals("Updated Branch"))
            .verifyComplete();
    }
    
    @Test
    void shouldFailUpdatingBranchNameWhenNotFound() {
        UpdateNameRequest request = new UpdateNameRequest("Updated Branch");
        
        when(branchRepository.findById("b1")).thenReturn(Mono.empty());
        
        StepVerifier.create(branchService.updateBranchName("b1", request))
            .expectError(BranchNotFoundException.class)
            .verify();
    }
}
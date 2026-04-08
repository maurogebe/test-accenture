package com.example.Accenture.application.service.impl;

import com.example.Accenture.application.dto.request.CreateBranchRequest;
import com.example.Accenture.application.dto.request.UpdateNameRequest;
import com.example.Accenture.application.dto.response.BranchResponse;
import com.example.Accenture.application.mapper.BranchDtoMapper;
import com.example.Accenture.application.service.BranchService;
import com.example.Accenture.domain.exception.BranchNotFoundException;
import com.example.Accenture.domain.exception.FranchiseNotFoundException;
import com.example.Accenture.domain.model.Branch;
import com.example.Accenture.infrastructure.persistence.mapper.BranchDocumentMapper;
import com.example.Accenture.infrastructure.persistence.repository.BranchRepository;
import com.example.Accenture.infrastructure.persistence.repository.FranchiseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class BranchServiceImpl implements BranchService {
    
    private final BranchRepository branchRepository;
    private final FranchiseRepository franchiseRepository;
    private final BranchDocumentMapper branchDocumentMapper;
    private final BranchDtoMapper branchDtoMapper;
    
    @Override
    public Mono<BranchResponse> createBranch(String franchiseId, CreateBranchRequest request) {
        return franchiseRepository.findById(franchiseId)
            .switchIfEmpty(Mono.error(new FranchiseNotFoundException(franchiseId)))
            .flatMap(franchiseDocument -> {
                Branch branch = Branch.builder()
                    .franchiseId(franchiseId)
                    .name(request.name())
                    .build();
                
                return branchRepository.save(branchDocumentMapper.toDocument(branch));
            })
            .map(branchDocumentMapper::toDomain)
            .map(branchDtoMapper::toResponse);
    }
    
    @Override
    public Mono<BranchResponse> updateBranchName(String branchId, UpdateNameRequest request) {
        return branchRepository.findById(branchId)
            .switchIfEmpty(Mono.error(new BranchNotFoundException(branchId)))
            .flatMap(document -> {
                document.setName(request.name());
                return branchRepository.save(document);
            })
            .map(branchDocumentMapper::toDomain)
            .map(branchDtoMapper::toResponse);
    }
}
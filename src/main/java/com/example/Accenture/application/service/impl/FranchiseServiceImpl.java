package com.example.Accenture.application.service.impl;

import com.example.Accenture.application.dto.request.CreateFranchiseRequest;
import com.example.Accenture.application.dto.request.UpdateNameRequest;
import com.example.Accenture.application.dto.response.FranchiseResponse;
import com.example.Accenture.application.dto.response.TopStockProductResponse;
import com.example.Accenture.application.mapper.FranchiseDtoMapper;
import com.example.Accenture.application.service.FranchiseService;
import com.example.Accenture.domain.exception.FranchiseNotFoundException;
import com.example.Accenture.domain.model.Branch;
import com.example.Accenture.domain.model.Franchise;
import com.example.Accenture.infrastructure.persistence.mapper.BranchDocumentMapper;
import com.example.Accenture.infrastructure.persistence.mapper.FranchiseDocumentMapper;
import com.example.Accenture.infrastructure.persistence.mapper.ProductDocumentMapper;
import com.example.Accenture.infrastructure.persistence.repository.BranchRepository;
import com.example.Accenture.infrastructure.persistence.repository.FranchiseRepository;
import com.example.Accenture.infrastructure.persistence.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class FranchiseServiceImpl implements FranchiseService {
    
    private final FranchiseRepository franchiseRepository;
    private final BranchRepository branchRepository;
    private final ProductRepository productRepository;
    
    private final FranchiseDocumentMapper franchiseDocumentMapper;
    private final BranchDocumentMapper branchDocumentMapper;
    private final ProductDocumentMapper productDocumentMapper;
    private final FranchiseDtoMapper franchiseDtoMapper;
    
    @Override
    public Mono<FranchiseResponse> createFranchise(CreateFranchiseRequest request) {
        Franchise franchise = Franchise.builder()
            .name(request.name())
            .build();
        
        return franchiseRepository.save(franchiseDocumentMapper.toDocument(franchise))
            .map(franchiseDocumentMapper::toDomain)
            .map(franchiseDtoMapper::toResponse);
    }
    
    @Override
    public Mono<FranchiseResponse> updateFranchiseName(String franchiseId, UpdateNameRequest request) {
        return franchiseRepository.findById(franchiseId)
            .switchIfEmpty(Mono.error(new FranchiseNotFoundException(franchiseId)))
            .flatMap(document -> {
                document.setName(request.name());
                return franchiseRepository.save(document);
            })
            .map(franchiseDocumentMapper::toDomain)
            .map(franchiseDtoMapper::toResponse);
    }
    
    @Override
    public Flux<TopStockProductResponse> getTopStockProductsByFranchise(String franchiseId) {
        return franchiseRepository.findById(franchiseId)
            .switchIfEmpty(Mono.error(new FranchiseNotFoundException(franchiseId)))
            .thenMany(branchRepository.findByFranchiseId(franchiseId))
            .flatMap(branchDocument -> {
                Branch branch = branchDocumentMapper.toDomain(branchDocument);
                
                return productRepository.findByBranchId(branch.id())
                    .map(productDocumentMapper::toDomain)
                    .sort((a, b) -> Integer.compare(b.stock(), a.stock()))
                    .next()
                    .map(product -> new TopStockProductResponse(
                        branch.id(),
                        branch.name(),
                        product.id(),
                        product.name(),
                        product.stock()
                    ));
            });
    }
}
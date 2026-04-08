package com.example.Accenture.infrastructure.persistence.repository;

import com.example.Accenture.infrastructure.persistence.document.BranchDocument;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface BranchRepository extends ReactiveMongoRepository<BranchDocument, String> {
    Flux<BranchDocument> findByFranchiseId(String franchiseId);
}
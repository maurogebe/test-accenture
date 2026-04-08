package com.example.Accenture.infrastructure.persistence.repository;

import com.example.Accenture.infrastructure.persistence.document.ProductDocument;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface ProductRepository extends ReactiveMongoRepository<ProductDocument, String> {
    Flux<ProductDocument> findByBranchId(String branchId);
}
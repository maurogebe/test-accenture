package com.example.Accenture.infrastructure.persistence.repository;

import com.example.Accenture.infrastructure.persistence.document.FranchiseDocument;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface FranchiseRepository extends ReactiveMongoRepository<FranchiseDocument, String> {
}
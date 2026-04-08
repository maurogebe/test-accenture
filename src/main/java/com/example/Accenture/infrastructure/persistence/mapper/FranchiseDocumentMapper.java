package com.example.Accenture.infrastructure.persistence.mapper;

import com.example.Accenture.domain.model.Franchise;
import com.example.Accenture.infrastructure.persistence.document.FranchiseDocument;
import org.springframework.stereotype.Component;

@Component
public class FranchiseDocumentMapper {
    
    public Franchise toDomain(FranchiseDocument document) {
        return Franchise.builder()
            .id(document.getId())
            .name(document.getName())
            .build();
    }
    
    public FranchiseDocument toDocument(Franchise franchise) {
        return FranchiseDocument.builder()
            .id(franchise.id())
            .name(franchise.name())
            .build();
    }
}
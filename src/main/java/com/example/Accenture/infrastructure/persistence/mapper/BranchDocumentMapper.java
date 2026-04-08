package com.example.Accenture.infrastructure.persistence.mapper;

import com.example.Accenture.domain.model.Branch;
import com.example.Accenture.infrastructure.persistence.document.BranchDocument;
import org.springframework.stereotype.Component;

@Component
public class BranchDocumentMapper {
    
    public Branch toDomain(BranchDocument document) {
        return Branch.builder()
            .id(document.getId())
            .franchiseId(document.getFranchiseId())
            .name(document.getName())
            .build();
    }
    
    public BranchDocument toDocument(Branch branch) {
        return BranchDocument.builder()
            .id(branch.id())
            .franchiseId(branch.franchiseId())
            .name(branch.name())
            .build();
    }
}
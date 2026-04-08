package com.example.Accenture.application.mapper;

import com.example.Accenture.application.dto.response.BranchResponse;
import com.example.Accenture.domain.model.Branch;
import org.springframework.stereotype.Component;

@Component
public class BranchDtoMapper {
    
    public BranchResponse toResponse(Branch branch) {
        return new BranchResponse(
            branch.id(),
            branch.franchiseId(),
            branch.name()
        );
    }
}
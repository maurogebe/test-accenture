package com.example.Accenture.application.mapper;

import com.example.Accenture.application.dto.response.FranchiseResponse;
import com.example.Accenture.domain.model.Franchise;
import org.springframework.stereotype.Component;

@Component
public class FranchiseDtoMapper {
    
    public FranchiseResponse toResponse(Franchise franchise) {
        return new FranchiseResponse(
            franchise.id(),
            franchise.name()
        );
    }
}
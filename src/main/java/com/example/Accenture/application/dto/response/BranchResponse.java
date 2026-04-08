package com.example.Accenture.application.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Branch response")
public record BranchResponse(
    
    @Schema(description = "Branch identifier", example = "662f8f2b9e1234567890abce")
    String id,
    
    @Schema(description = "Franchise identifier", example = "662f8f2b9e1234567890abcd")
    String franchiseId,
    
    @Schema(description = "Branch name", example = "Medellin Branch")
    String name
) {
}

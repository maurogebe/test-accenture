package com.example.Accenture.application.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Franchise response")
public record FranchiseResponse(
    
    @Schema(description = "Franchise identifier", example = "662f8f2b9e1234567890abcd")
    String id,
    
    @Schema(description = "Franchise name", example = "North Region Franchise")
    String name
) {
}
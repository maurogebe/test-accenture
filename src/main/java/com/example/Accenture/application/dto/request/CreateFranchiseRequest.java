package com.example.Accenture.application.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Request to create a new franchise")
public record CreateFranchiseRequest(
    
    @Schema(description = "Franchise name", example = "North Region Franchise")
    @NotBlank(message = "Franchise name is required")
    String name
) {
}

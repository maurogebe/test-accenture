package com.example.Accenture.application.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Request to create a new branch")
public record CreateBranchRequest(
    
    @Schema(description = "Branch name", example = "Medellin Branch")
    @NotBlank(message = "Branch name is required")
    String name
) {
}
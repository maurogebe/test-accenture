package com.example.Accenture.application.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Request to update a name")
public record UpdateNameRequest(
    
    @Schema(description = "New name value", example = "Updated Name")
    @NotBlank(message = "Name is required")
    String name
) {
}
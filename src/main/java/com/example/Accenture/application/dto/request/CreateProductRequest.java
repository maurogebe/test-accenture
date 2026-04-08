package com.example.Accenture.application.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Request to create a new product")
public record CreateProductRequest(
    
    @Schema(description = "Product name", example = "Hair Gel")
    @NotBlank(message = "Product name is required")
    String name,
    
    @Schema(description = "Initial stock for the product", example = "50")
    @NotNull(message = "Stock is required")
    @Min(value = 0, message = "Stock must be greater than or equal to 0")
    Integer stock
) {
}
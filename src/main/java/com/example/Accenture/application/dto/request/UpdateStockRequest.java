package com.example.Accenture.application.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Request to update product stock")
public record UpdateStockRequest(
    
    @Schema(description = "New stock value", example = "100")
    @NotNull(message = "Stock is required")
    @Min(value = 0, message = "Stock must be greater than or equal to 0")
    Integer stock
) {
}
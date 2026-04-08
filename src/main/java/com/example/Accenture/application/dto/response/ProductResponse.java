package com.example.Accenture.application.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Product response")
public record ProductResponse(
    
    @Schema(description = "Product identifier", example = "662f8f2b9e1234567890abcf")
    String id,
    
    @Schema(description = "Branch identifier", example = "662f8f2b9e1234567890abce")
    String branchId,
    
    @Schema(description = "Product name", example = "Hair Gel")
    String name,
    
    @Schema(description = "Available stock", example = "100")
    Integer stock
) {
}
package com.example.Accenture.application.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Product with the highest stock per branch")
public record TopStockProductResponse(
    
    @Schema(description = "Branch identifier", example = "662f8f2b9e1234567890abce")
    String branchId,
    
    @Schema(description = "Branch name", example = "Medellin Branch")
    String branchName,
    
    @Schema(description = "Product identifier", example = "662f8f2b9e1234567890abcf")
    String productId,
    
    @Schema(description = "Product name", example = "Hair Gel")
    String productName,
    
    @Schema(description = "Highest stock value", example = "150")
    Integer stock
) {
}
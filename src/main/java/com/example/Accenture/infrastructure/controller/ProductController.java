package com.example.Accenture.infrastructure.controller;

import com.example.Accenture.application.dto.request.CreateProductRequest;
import com.example.Accenture.application.dto.request.UpdateNameRequest;
import com.example.Accenture.application.dto.request.UpdateStockRequest;
import com.example.Accenture.application.dto.response.ProductResponse;
import com.example.Accenture.application.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Tag(name = "Products", description = "Operations related to product management")
public class ProductController {
    
    private final ProductService productService;
    
    @Operation(summary = "Create a product", description = "Creates a new product under a branch")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Product created successfully"),
        @ApiResponse(responseCode = "404", description = "Branch not found",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("/branches/{branchId}/products")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<ProductResponse> createProduct(
        @PathVariable String branchId,
        @Valid @RequestBody CreateProductRequest request
    ) {
        return productService.createProduct(branchId, request);
    }
    
    @Operation(summary = "Delete a product", description = "Deletes a product from a branch")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Product deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Branch or product not found",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @DeleteMapping("/branches/{branchId}/products/{productId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteProduct(
        @PathVariable String branchId,
        @PathVariable String productId
    ) {
        return productService.deleteProduct(branchId, productId);
    }
    
    @Operation(summary = "Update product stock", description = "Updates the stock of an existing product")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Product stock updated successfully"),
        @ApiResponse(responseCode = "404", description = "Product not found",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PatchMapping("/products/{productId}/stock")
    public Mono<ProductResponse> updateProductStock(
        @PathVariable String productId,
        @Valid @RequestBody UpdateStockRequest request
    ) {
        return productService.updateProductStock(productId, request);
    }
    
    @Operation(summary = "Update product name", description = "Updates the name of an existing product")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Product name updated successfully"),
        @ApiResponse(responseCode = "404", description = "Product not found",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PatchMapping("/products/{productId}/name")
    public Mono<ProductResponse> updateProductName(
        @PathVariable String productId,
        @Valid @RequestBody UpdateNameRequest request
    ) {
        return productService.updateProductName(productId, request);
    }
}
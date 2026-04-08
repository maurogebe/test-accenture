package com.example.Accenture.infrastructure.controller;

import com.example.Accenture.application.dto.request.CreateFranchiseRequest;
import com.example.Accenture.application.dto.request.UpdateNameRequest;
import com.example.Accenture.application.dto.response.FranchiseResponse;
import com.example.Accenture.application.dto.response.TopStockProductResponse;
import com.example.Accenture.application.service.FranchiseService;
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
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/franchises")
@RequiredArgsConstructor
@Tag(name = "Franchises", description = "Operations related to franchise management")
public class FranchiseController {
    
    private final FranchiseService franchiseService;
    
    @Operation(summary = "Create a franchise", description = "Creates a new franchise")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Product created successfully"),
        @ApiResponse(responseCode = "404", description = "Branch not found",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<FranchiseResponse> createFranchise(@Valid @RequestBody CreateFranchiseRequest request) {
        return franchiseService.createFranchise(request);
    }
    
    @Operation(summary = "Update franchise name", description = "Updates the name of an existing franchise")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Franchise updated successfully"),
        @ApiResponse(responseCode = "404", description = "Franchise not found",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PatchMapping("/{franchiseId}/name")
    public Mono<FranchiseResponse> updateFranchiseName(
        @PathVariable String franchiseId,
        @Valid @RequestBody UpdateNameRequest request
    ) {
        return franchiseService.updateFranchiseName(franchiseId, request);
    }
    
    @Operation(
        summary = "Get top stock products by franchise",
        description = "Returns the product with the highest stock for each branch of a given franchise"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Query executed successfully"),
        @ApiResponse(responseCode = "404", description = "Franchise not found",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/{franchiseId}/top-stock-products")
    public Flux<TopStockProductResponse> getTopStockProducts(@PathVariable String franchiseId) {
        return franchiseService.getTopStockProductsByFranchise(franchiseId);
    }
}
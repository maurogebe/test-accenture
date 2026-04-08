package com.example.Accenture.infrastructure.controller;

import com.example.Accenture.application.dto.request.CreateBranchRequest;
import com.example.Accenture.application.dto.request.UpdateNameRequest;
import com.example.Accenture.application.dto.response.BranchResponse;
import com.example.Accenture.application.service.BranchService;
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
@Tag(name = "Branches", description = "Operations related to branch management")
public class BranchController {
    
    private final BranchService branchService;
    
    @Operation(summary = "Create a branch", description = "Creates a new branch under a franchise")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Branch created successfully"),
        @ApiResponse(responseCode = "404", description = "Franchise not found",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("/franchises/{franchiseId}/branches")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<BranchResponse> createBranch(
        @PathVariable String franchiseId,
        @Valid @RequestBody CreateBranchRequest request
    ) {
        return branchService.createBranch(franchiseId, request);
    }
    
    @Operation(summary = "Update branch name", description = "Updates the name of an existing branch")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Branch updated successfully"),
        @ApiResponse(responseCode = "404", description = "Branch not found",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PatchMapping("/branches/{branchId}/name")
    public Mono<BranchResponse> updateBranchName(
        @PathVariable String branchId,
        @Valid @RequestBody UpdateNameRequest request
    ) {
        return branchService.updateBranchName(branchId, request);
    }
}
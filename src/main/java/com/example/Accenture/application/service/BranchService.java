package com.example.Accenture.application.service;

import com.example.Accenture.application.dto.request.CreateBranchRequest;
import com.example.Accenture.application.dto.request.UpdateNameRequest;
import com.example.Accenture.application.dto.response.BranchResponse;
import reactor.core.publisher.Mono;

public interface BranchService {
    Mono<BranchResponse> createBranch(String franchiseId, CreateBranchRequest request);
    Mono<BranchResponse> updateBranchName(String branchId, UpdateNameRequest request);
}
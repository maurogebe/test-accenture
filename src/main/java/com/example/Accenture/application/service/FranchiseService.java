package com.example.Accenture.application.service;

import com.example.Accenture.application.dto.request.CreateFranchiseRequest;
import com.example.Accenture.application.dto.request.UpdateNameRequest;
import com.example.Accenture.application.dto.response.FranchiseResponse;
import com.example.Accenture.application.dto.response.TopStockProductResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface FranchiseService {
    Mono<FranchiseResponse> createFranchise(CreateFranchiseRequest request);
    Mono<FranchiseResponse> updateFranchiseName(String franchiseId, UpdateNameRequest request);
    Flux<TopStockProductResponse> getTopStockProductsByFranchise(String franchiseId);
}
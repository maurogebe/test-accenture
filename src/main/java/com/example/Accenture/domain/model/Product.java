package com.example.Accenture.domain.model;

import lombok.Builder;

@Builder
public record Product(
    String id,
    String branchId,
    String name,
    Integer stock
) {
}

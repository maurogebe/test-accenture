package com.example.Accenture.domain.model;

import lombok.Builder;

@Builder
public record Branch(
    String id,
    String franchiseId,
    String name
) {
}
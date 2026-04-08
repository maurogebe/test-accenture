package com.example.Accenture.domain.model;

import lombok.Builder;

@Builder
public record Franchise(
    String id,
    String name
) {
}

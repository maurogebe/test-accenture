package com.example.Accenture.infrastructure.handler;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(description = "Standard error response")
public record ErrorResponse(
    
    @Schema(description = "Timestamp of the error", example = "2026-04-07T12:00:00")
    LocalDateTime timestamp,
    
    @Schema(description = "HTTP status code", example = "404")
    int status,
    
    @Schema(description = "Error type", example = "Not Found")
    String error,
    
    @Schema(description = "Detailed error message", example = "Franchise not found")
    String message
) {
}

package com.nassreml.swapi.proxy.api.inbound.controllers.dtos;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Error response schema")
public record ErrorResponseDto(
        @Schema(description = "Error type")
        String error,
        @Schema(description = "Error description")
        String message,
        @Schema(description = "HTTP status code")
        int status,
        @Schema(description = "Timestamp of the error")
        String timestamp
) {}
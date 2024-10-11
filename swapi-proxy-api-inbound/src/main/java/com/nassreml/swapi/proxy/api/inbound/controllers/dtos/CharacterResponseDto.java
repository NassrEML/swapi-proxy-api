package com.nassreml.swapi.proxy.api.inbound.controllers.dtos;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "Character response schema")
public record CharacterResponseDto(
        @Schema(description = "Name of the character")
        String name,
        @Schema(description = "Birth year of the character")
        String birth_year,
        @Schema(description = "Gender of the character")
        String gender,
        @Schema(description = "Name of the planet the character is from")
        String planet_name,
        @Schema(description = "Name of the fastest vehicle driven by the character")
        String fastest_vehicle_driven,
        @Schema(description = "List of films the character appeared in")
        List<FilmResponseDto> films) {
}

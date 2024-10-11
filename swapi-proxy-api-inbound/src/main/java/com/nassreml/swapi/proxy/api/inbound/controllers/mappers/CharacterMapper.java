package com.nassreml.swapi.proxy.api.inbound.controllers.mappers;

import com.nassreml.swapi.proxy.api.core.domain.Character;
import com.nassreml.swapi.proxy.api.inbound.controllers.dtos.CharacterResponseDto;
import com.nassreml.swapi.proxy.api.inbound.controllers.dtos.FilmResponseDto;

public class CharacterMapper {
    public static CharacterResponseDto toDto(Character character) {
        return new CharacterResponseDto(
                character.name(),
                character.birthYear(),
                character.gender(),
                character.planetName(),
                character.fastestVehicleDriven(),
                character.films() == null ? null : character.films().stream()
                        .map(film -> new FilmResponseDto(film.title(), film.releaseDate()))
                        .toList()
        );
    }
}

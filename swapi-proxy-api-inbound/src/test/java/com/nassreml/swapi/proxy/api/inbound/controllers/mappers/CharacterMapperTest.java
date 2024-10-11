package com.nassreml.swapi.proxy.api.inbound.controllers.mappers;

import com.nassreml.swapi.proxy.api.core.domain.Character;
import com.nassreml.swapi.proxy.api.core.domain.Film;
import com.nassreml.swapi.proxy.api.core.domain.Vehicle;
import com.nassreml.swapi.proxy.api.inbound.controllers.dtos.CharacterResponseDto;
import com.nassreml.swapi.proxy.api.inbound.controllers.dtos.FilmResponseDto;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class CharacterMapperTest {

    @Test
    void shouldMapCharacterToDto_withAllFields() {
        Character character = new Character(
                "Luke Skywalker",
                "19BBY",
                "male",
                "Tatooine",
                List.of(new Vehicle("X-Wing", 1050)),
                List.of(new Film("A New Hope", "1977-05-25"))
        );

        CharacterResponseDto dto = CharacterMapper.toDto(character);

        assertEquals("Luke Skywalker", dto.name());
        assertEquals("19BBY", dto.birth_year());
        assertEquals("male", dto.gender());
        assertEquals("Tatooine", dto.planet_name());
        assertEquals("X-Wing", dto.fastest_vehicle_driven());
        assertEquals(1, dto.films().size());
        assertEquals(new FilmResponseDto("A New Hope", "1977-05-25"), dto.films().get(0));
    }

    @Test
    void shouldMapCharacterToDto_withEmptyLists() {
        Character character = new Character(
                "Luke Skywalker",
                "19BBY",
                "male",
                "Tatooine",
                List.of(),
                List.of()
        );

        CharacterResponseDto dto = CharacterMapper.toDto(character);

        assertEquals("Luke Skywalker", dto.name());
        assertEquals("19BBY", dto.birth_year());
        assertEquals("male", dto.gender());
        assertEquals("Tatooine", dto.planet_name());
        assertNull(dto.fastest_vehicle_driven());
        assertEquals(0, dto.films().size());
    }

    @Test
    void shouldMapCharacterToDto_withNullValues() {
        Character character = new Character(
                null,
                null,
                null,
                null,
                List.of(),
                List.of()
        );

        CharacterResponseDto dto = CharacterMapper.toDto(character);

        assertNull(dto.name());
        assertNull(null, dto.birth_year());
        assertNull(null, dto.gender());
        assertNull(null, dto.planet_name());
        assertNull(null, dto.fastest_vehicle_driven());
        assertEquals(0, dto.films().size());
    }
}
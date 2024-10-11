package com.nassreml.swapi.proxy.api.outbound.mappers;

import com.nassreml.swapi.proxy.api.core.domain.Character;
import com.nassreml.swapi.proxy.api.core.domain.Film;
import com.nassreml.swapi.proxy.api.core.domain.Vehicle;
import com.nassreml.swapi.proxy.api.outbound.dtos.FilmDto;
import com.nassreml.swapi.proxy.api.outbound.dtos.PersonDto;
import com.nassreml.swapi.proxy.api.outbound.dtos.PlanetDto;
import com.nassreml.swapi.proxy.api.outbound.dtos.VehicleDto;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PersonMapperTest {

    @Test
    void shouldMapToDomainWhenGivenValidPersonDto() {
        PersonDto personDto = new PersonDto(
                "Luke Skywalker",
                null,
                null,
                null,
                null,
                null,
                "19BBY",
                "male",
                "http://swapi.dev/api/planets/1/",
                List.of("http://swapi.dev/api/films/1/"),
                null,
                List.of(),
                List.of("http://swapi.dev/api/starships/1/"),
                null,
                null,
                null
        );
        PlanetDto planetDto = new PlanetDto("Tatooine");
        VehicleDto vehicleDto = new VehicleDto("Snowspeeder", "650");
        FilmDto filmDto = new FilmDto("A New Hope", "1977-05-25");

        List<VehicleDto> vehiclesDto = List.of(vehicleDto);
        List<FilmDto> filmsDto = List.of(filmDto);

        Character expectedCharacter = new Character(
                "Luke Skywalker",
                "19BBY",
                "male",
                "Tatooine",
                List.of(new Vehicle("Snowspeeder", 650)),
                List.of(new Film("A New Hope", "1977-05-25"))
        );

        Character actualCharacter = PersonMapper.toDomain(personDto, planetDto, vehiclesDto, filmsDto);

        assertEquals(expectedCharacter, actualCharacter);
    }
}
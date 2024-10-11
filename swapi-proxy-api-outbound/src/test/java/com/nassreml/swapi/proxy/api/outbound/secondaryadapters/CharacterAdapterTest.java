package com.nassreml.swapi.proxy.api.outbound.secondaryadapters;

import com.nassreml.swapi.proxy.api.core.domain.Character;
import com.nassreml.swapi.proxy.api.core.domain.Film;
import com.nassreml.swapi.proxy.api.core.domain.Vehicle;
import com.nassreml.swapi.proxy.api.outbound.dtos.FilmDto;
import com.nassreml.swapi.proxy.api.outbound.dtos.PersonDto;
import com.nassreml.swapi.proxy.api.outbound.dtos.PlanetDto;
import com.nassreml.swapi.proxy.api.outbound.dtos.VehicleDto;
import com.nassreml.swapi.proxy.api.outbound.http.SwapiHttpRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


class CharacterAdapterTest {

    private SwapiHttpRepository swapiRepository;
    private CharacterAdapter characterAdapter;

    @BeforeEach
    void setUp() {
        swapiRepository = mock(SwapiHttpRepository.class);
        characterAdapter = new CharacterAdapter(swapiRepository);
    }

    @Test
    void shouldGetCharacterWhenGivenValidCharacterName() {
        String characterName = "Luke Skywalker";
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
                null);
        PlanetDto planetDto = new PlanetDto("Tatooine");
        VehicleDto vehicleDto = new VehicleDto("Snowspeeder", "650");
        FilmDto filmDto = new FilmDto("A New Hope", "1977-05-25");

        when(swapiRepository.getPersonName(characterName)).thenReturn(personDto);
        when(swapiRepository.getPlanetName(personDto.homeworld())).thenReturn(planetDto);
        when(swapiRepository.getVehicle("http://swapi.dev/api/starships/1/")).thenReturn(vehicleDto);
        when(swapiRepository.getFilm("http://swapi.dev/api/films/1/")).thenReturn(filmDto);

        Character expectedCharacter = new Character(
                "Luke Skywalker",
                "19BBY",
                "male",
                "Tatooine",
                List.of(new Vehicle("Snowspeeder", 650)),
                List.of(new Film("A New Hope", "1977-05-25"))
        );

        Character actualCharacter = characterAdapter.getCharacter(characterName);

        assertEquals(expectedCharacter, actualCharacter);
        verify(swapiRepository).getPersonName(characterName);
        verify(swapiRepository).getPlanetName(personDto.homeworld());
        verify(swapiRepository, times(1)).getVehicle(anyString());
        verify(swapiRepository).getFilm("http://swapi.dev/api/films/1/");
    }
}
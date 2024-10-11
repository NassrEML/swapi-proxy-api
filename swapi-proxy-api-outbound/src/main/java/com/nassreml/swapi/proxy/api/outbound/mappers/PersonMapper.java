package com.nassreml.swapi.proxy.api.outbound.mappers;

import com.nassreml.swapi.proxy.api.core.domain.Character;
import com.nassreml.swapi.proxy.api.core.domain.Film;
import com.nassreml.swapi.proxy.api.core.domain.Vehicle;
import com.nassreml.swapi.proxy.api.outbound.dtos.FilmDto;
import com.nassreml.swapi.proxy.api.outbound.dtos.PersonDto;
import com.nassreml.swapi.proxy.api.outbound.dtos.PlanetDto;
import com.nassreml.swapi.proxy.api.outbound.dtos.VehicleDto;

import java.util.List;

public class PersonMapper {
    public static Character toDomain(PersonDto personDto, PlanetDto planetDto, List<VehicleDto> vehiclesDto, List<FilmDto> filmsDto) {
        return new Character(
                personDto.name(),
                personDto.birth_year(),
                personDto.gender(),
                planetDto.name(),
                vehiclesDto.stream()
                        .map(vehicleDto -> new Vehicle(vehicleDto.name(), vehicleDto.maxAtmosphericSpeed()))
                        .toList(),
                filmsDto.stream()
                        .map(filmDto -> new Film(filmDto.title(), filmDto.release_date()))
                        .toList()
        );
    }
}

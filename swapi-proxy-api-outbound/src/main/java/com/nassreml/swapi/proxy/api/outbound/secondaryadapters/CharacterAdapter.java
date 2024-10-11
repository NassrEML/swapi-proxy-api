package com.nassreml.swapi.proxy.api.outbound.secondaryadapters;

import com.nassreml.swapi.proxy.api.core.domain.Character;
import com.nassreml.swapi.proxy.api.core.domain.ports.CharacterPort;
import com.nassreml.swapi.proxy.api.outbound.dtos.FilmDto;
import com.nassreml.swapi.proxy.api.outbound.dtos.VehicleDto;
import com.nassreml.swapi.proxy.api.outbound.http.SwapiHttpRepository;
import com.nassreml.swapi.proxy.api.outbound.mappers.PersonMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CharacterAdapter implements CharacterPort {

    private final SwapiHttpRepository swapiRepository;

    public CharacterAdapter(SwapiHttpRepository swapiRepository) {
        this.swapiRepository = swapiRepository;
    }

    @Override
    public Character getCharacter(String name) {
        var personDto = swapiRepository.getPersonName(name);

        if (personDto == null) {
            return null;
        }

        var planetDto = swapiRepository.getPlanetName(personDto.homeworld());
        var vehiclesDto = getVehicles(personDto.vehicles(), personDto.starships());
        var filmsDto = getFilms(personDto.films());

        return PersonMapper.toDomain(personDto, planetDto, vehiclesDto, filmsDto);
    }

    private List<VehicleDto> getVehicles(List<String> vehiclesUrl, List<String> starshipsUrl) {
        final List<String> urls = flatLists(vehiclesUrl, starshipsUrl);
        return urls.stream()
                .map(swapiRepository::getVehicle)
                .toList();
    }

    private List<String> flatLists(List<String> vehiclesUrl, List<String> starshipsUrl) {
        final List<String> urls = new ArrayList<>(vehiclesUrl);
        urls.addAll(starshipsUrl);
        return urls;
    }

    private List<FilmDto> getFilms(List<String> filmsUrl) {
        return filmsUrl.stream()
                .map(swapiRepository::getFilm)
                .toList();
    }
}

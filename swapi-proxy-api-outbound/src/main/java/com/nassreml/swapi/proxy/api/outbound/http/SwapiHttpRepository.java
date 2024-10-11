package com.nassreml.swapi.proxy.api.outbound.http;

import com.nassreml.swapi.proxy.api.outbound.dtos.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class SwapiHttpRepository {

    private final WebClient webClient;

    @Value("${swapi.base-url}")
    private String BASE_URL;

    private static final String PEOPLE_ENDPOINT = "people/?search={characterName}";

    @Autowired
    public SwapiHttpRepository(WebClient webClient) {
        this.webClient = webClient;
    }

    @Cacheable("person")
    public PersonDto getPersonName(String name) {
        SwapiResponseDto<PersonDto> response = getPeopleResponse(name);

        if (response != null && response.count() >= 1) {
            return response.results().getFirst();
        }

        return null;
    }

    @Cacheable("planet")
    public PlanetDto getPlanetName(String url) {
        return getPlanetResponse(url);
    }

    @Cacheable("vehicle")
    public VehicleDto getVehicle(String url) {
        return getVehicleResponse(url);
    }

    @Cacheable("film")
    public FilmDto getFilm(String url) {
        return getFilmResponse(url);
    }

    private String buildUrl() {
        return BASE_URL + SwapiHttpRepository.PEOPLE_ENDPOINT;
    }

    private <T> T getResponse(String url, ParameterizedTypeReference<T> responseType, Object... uriVariables) {
        return webClient.get()
                .uri(url, uriVariables)
                .retrieve()
                .bodyToMono(responseType)
                .block();
    }

    private SwapiResponseDto<PersonDto> getPeopleResponse(String characterName) {
        return getResponse(buildUrl(), new ParameterizedTypeReference<SwapiResponseDto<PersonDto>>() {
        }, characterName);
    }

    private PlanetDto getPlanetResponse(String planetUrl) {
        return getResponse(planetUrl, new ParameterizedTypeReference<PlanetDto>() {
        });
    }

    private VehicleDto getVehicleResponse(String vehicleUrl) {
        return getResponse(vehicleUrl, new ParameterizedTypeReference<VehicleDto>() {
        });
    }

    private FilmDto getFilmResponse(String filmUrl) {
        return getResponse(filmUrl, new ParameterizedTypeReference<FilmDto>() {
        });
    }


}
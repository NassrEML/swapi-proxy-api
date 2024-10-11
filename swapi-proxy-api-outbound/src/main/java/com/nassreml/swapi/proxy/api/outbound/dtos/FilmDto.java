package com.nassreml.swapi.proxy.api.outbound.dtos;

import java.io.Serializable;

public record FilmDto(String title, String release_date) implements Serializable {
}

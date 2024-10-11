package com.nassreml.swapi.proxy.api.outbound.dtos;

import java.io.Serializable;
import java.util.List;

public record SwapiResponseDto<T>(
        int count,
        String next,
        String previous,
        List<T> results
) implements Serializable {
}
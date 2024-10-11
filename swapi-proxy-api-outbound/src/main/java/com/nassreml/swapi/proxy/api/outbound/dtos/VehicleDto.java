package com.nassreml.swapi.proxy.api.outbound.dtos;

import java.io.Serializable;

public record VehicleDto(String name, String max_atmosphering_speed) implements Serializable {

    public int maxAtmosphericSpeed() {
        return Integer.parseInt(max_atmosphering_speed);
    }

}

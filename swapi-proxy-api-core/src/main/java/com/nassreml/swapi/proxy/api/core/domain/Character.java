package com.nassreml.swapi.proxy.api.core.domain;

import java.util.Comparator;
import java.util.List;

public record Character(
        String name,
        String birthYear,
        String gender,
        String planetName,
        List<Vehicle> vehicles,
        List<Film> films
) {
    public String fastestVehicleDriven() {
        return vehicles.stream()
                .max(Comparator.comparing(Vehicle::maxAtmosphericSpeed))
                .map(Vehicle::name)
                .orElse(null);
    }
}

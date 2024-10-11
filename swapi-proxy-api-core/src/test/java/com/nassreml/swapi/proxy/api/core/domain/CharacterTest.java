package com.nassreml.swapi.proxy.api.core.domain;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CharacterTest {

    @ParameterizedTest
    @MethodSource("provideTestCases")
    void shouldReturnTheFastestVehicle_whenGivenListOfVehicles(List<Vehicle> vehicles, String expectedVehicle) {
        Character character = new Character(
                "Luke Skywalker",
                "19BBY",
                "male",
                "Tatooine",
                vehicles,
                null
        );

        String vehicle = character.fastestVehicleDriven();

        assertEquals(vehicle, expectedVehicle);
    }

    private static Stream<Arguments> provideTestCases() {
        Vehicle vehicle1 = new Vehicle("X-Wing", 1050);
        Vehicle vehicle2 = new Vehicle("TIE Fighter", 1200);
        Vehicle vehicle3 = new Vehicle("Millennium Falcon", 1050);
        Vehicle vehicle4 = new Vehicle("Y-Wing", 1000);
        Vehicle vehicle5 = new Vehicle("A-Wing", 1300);
        Vehicle vehicle6 = new Vehicle("Slave 1", 1050);
        Vehicle vehicle7 = new Vehicle("Imperial Shuttle", 850);
        Vehicle vehicle8 = new Vehicle("EF76 Nebulon-B escort frigate", 800);
        Vehicle vehicle9 = new Vehicle("Calamari Cruiser", 900);

        return Stream.of(
                Arguments.of(List.of(vehicle1, vehicle2, vehicle3, vehicle4, vehicle5), "A-Wing"),
                Arguments.of(List.of(vehicle4, vehicle6, vehicle7, vehicle8, vehicle9), "Slave 1"),
                Arguments.of(List.of(), null),
                Arguments.of(List.of(vehicle1), "X-Wing"),
                Arguments.of(List.of(vehicle1, vehicle6), "X-Wing")
        );
    }
}
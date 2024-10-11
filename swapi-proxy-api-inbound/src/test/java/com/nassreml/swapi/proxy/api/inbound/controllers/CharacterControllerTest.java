package com.nassreml.swapi.proxy.api.inbound.controllers;

import com.nassreml.swapi.proxy.api.core.application.usecases.GetCharacterQry;
import com.nassreml.swapi.proxy.api.core.domain.Character;
import com.nassreml.swapi.proxy.api.inbound.controllers.mappers.CharacterMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class CharacterControllerTest {

    @Mock
    private GetCharacterQry getCharacterQry;

    @InjectMocks
    private CharacterController characterController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldReturnCharacter_whenValidNameIsGiven() {
        Character character = new Character("Luke Skywalker", "19BBY", "male", "Tatooine", List.of(), List.of());
        when(getCharacterQry.execute(anyString())).thenReturn(character);

        ResponseEntity<?> response = characterController.getCharacter("Luke Skywalker");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(CharacterMapper.toDto(character), response.getBody());
    }

    @Test
    void shouldReturnBadRequest_whenInvalidNameIsGiven() {
        ResponseEntity<?> response = characterController.getCharacter(" ");

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void shouldReturnNotFound_whenCharacterIsNotFound() {
        when(getCharacterQry.execute(anyString())).thenReturn(null);

        ResponseEntity<?> response = characterController.getCharacter("Unknown Character");

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @ParameterizedTest
    @MethodSource("provideTestCasesForBadRequest")
    void shouldReturnTheFastestVehicle_whenGivenListOfVehicles(String name) {
        ResponseEntity<?> response = characterController.getCharacter(name);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    private static Stream<Arguments> provideTestCasesForBadRequest() {
        final String name1 = null;
        final String name2 = "";
        final String name3 = " ";
        final String name4 = "     ";
        final String name5 = "  a  ";
        final String name6 = "123456789123456789123456789123456789";
        final String name7 = "1234567 8912345678912345678 9123456789";

        return Stream.of(
                Arguments.of(name1),
                Arguments.of(name2),
                Arguments.of(name3),
                Arguments.of(name4),
                Arguments.of(name5),
                Arguments.of(name6),
                Arguments.of(name7)
        );
    }


}
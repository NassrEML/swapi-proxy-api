package com.nassreml.swapi.proxy.api;

import com.nassreml.swapi.proxy.api.inbound.controllers.dtos.CharacterResponseDto;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SwapiProxyApiApplicationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    public static Stream<Arguments> provideTestCases() {
        return Stream.of(
                Arguments.of("lu", "Luke Skywalker"),
                Arguments.of("c-3", "C-3PO"),
                Arguments.of("r2", "R2-D2"),
                Arguments.of("darth   ", "Darth Vader"),
                Arguments.of("ia  Or", "Leia Organa"),
                Arguments.of("owen", "Owen Lars"),
                Arguments.of("      beru", "Beru Whitesun lars"),
                Arguments.of("DARKLIGHTER", "Biggs Darklighter"),
                Arguments.of(" bi-Wa ", "Obi-Wan Kenobi")
        );
    }

    @ParameterizedTest
    @MethodSource("provideTestCases")
    void shouldReturnCharacterWhenCallingSearcherGivenACharacterName(String characterName, String expectedCharacterName) {
        String url = "http://localhost:" + port + "/swapi-proxy/person-info?name=" + characterName;
        ResponseEntity<CharacterResponseDto> response = restTemplate.getForEntity(url, CharacterResponseDto.class);
        CharacterResponseDto characterResponseDto = response.getBody();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(characterResponseDto.name(), expectedCharacterName);
    }

}


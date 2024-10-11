package com.nassreml.swapi.proxy.api.inbound.controllers;

import com.nassreml.swapi.proxy.api.core.application.usecases.GetCharacterQry;
import com.nassreml.swapi.proxy.api.core.domain.Character;
import com.nassreml.swapi.proxy.api.inbound.controllers.dtos.CharacterResponseDto;
import com.nassreml.swapi.proxy.api.inbound.controllers.dtos.ErrorResponseDto;
import com.nassreml.swapi.proxy.api.inbound.controllers.mappers.CharacterMapper;
import com.nassreml.swapi.proxy.api.inbound.controllers.openapi.CharacterControllerOpenApi;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/swapi-proxy")
public class CharacterController implements CharacterControllerOpenApi {

    private final GetCharacterQry getCharacterQry;

    public CharacterController(GetCharacterQry getCharacterQry) {
        this.getCharacterQry = getCharacterQry;
    }

    @Override
    @GetMapping(
            value = "/person-info",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> getCharacter(@RequestParam("name") String characterName) {
        final String cleanedName = cleanSearchString(characterName);

        if (isInvalidName(cleanedName)) {
            return createErrorResponse(
                    "Validation Error",
                    "The 'name' parameter cannot be empty or a space, and must be between 2 and 50 characters long.",
                    HttpStatus.BAD_REQUEST
            );
        }

        Character character = getCharacterQry.execute(cleanedName);

        if (character == null) {
            return createErrorResponse(
                    "Character not found",
                    "Couldn't find character: " + cleanedName,
                    HttpStatus.NOT_FOUND
            );
        }

        CharacterResponseDto characterDto = CharacterMapper.toDto(character);
        return ResponseEntity.ok(characterDto);
    }

    private String cleanSearchString(String input) {
        if (input == null) {
            return null;
        }
        String cleaned = input.trim();
        cleaned = cleaned.toLowerCase();
        cleaned = cleaned.replaceAll("\\s+", " ");

        return cleaned;
    }

    private boolean isInvalidName(String name) {
        return name == null || name.length() < 2 || name.length() > 30;
    }

    private ResponseEntity<ErrorResponseDto> createErrorResponse(String error, String message, HttpStatus status) {
        ErrorResponseDto errorResponse = new ErrorResponseDto(
                error,
                message,
                status.value(),
                LocalDateTime.now().toString()
        );
        return ResponseEntity.status(status).body(errorResponse);
    }
}
package com.nassreml.swapi.proxy.api.inbound.controllers.openapi;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "Character", description = "Operations related to Star Wars characters")
public interface CharacterControllerOpenApi {

    @Operation(
            summary = "Get character information by name",
            description = "Returns detailed character information for a given name, including films and vehicles."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Character found",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(name = "CharacterResponse",
                                    value = """
                                            {
                                                "name": "Luke Skywalker",
                                                "birth_year": "19BBY",
                                                "gender": "male",
                                                "planet_name": "Tatooine",
                                                "fastest_vehicle_driven": "X-wing",
                                                "films": [
                                                    {
                                                        "name": "A New Hope",
                                                        "release_date": "1977-05-25"
                                                    },
                                                    {
                                                        "name": "The Empire Strikes Back",
                                                        "release_date": "1980-05-17"
                                                    },
                                                    {
                                                        "name": "Return of the Jedi",
                                                        "release_date": "1983-05-25"
                                                    },
                                                    {
                                                        "name": "Revenge of the Sith",
                                                        "release_date": "2005-05-19"
                                                    }
                                                ]
                                            }
                                            """))),
            @ApiResponse(responseCode = "400", description = "Validation Error",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(name = "ValidationError",
                                    value = """
                                            {
                                                "error": "Validation Error",
                                                "message": "The 'name' parameter cannot be empty or a space, and must be between 2 and 50 characters long.",
                                                "status": 400,
                                                "timestamp": "2024-09-04T21:03:59.906438637"
                                            }
                                            """))),
            @ApiResponse(responseCode = "404", description = "Character not found",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(name = "CharacterNotFound",
                                    value = """
                                            {
                                                "error": "Character not found",
                                                "message": "Couldn't find character: nassr",
                                                "status": 404,
                                                "timestamp": "2024-09-04T21:04:29.287231970"
                                            }
                                            """)))
    })
    ResponseEntity<?> getCharacter(
            @Parameter(description = "Name of the character to search for", required = true)
            String characterName);
}
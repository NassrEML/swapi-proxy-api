package com.nassreml.swapi.proxy.api.core.application.usecases;

import com.nassreml.swapi.proxy.api.core.domain.Character;
import com.nassreml.swapi.proxy.api.core.domain.ports.CharacterPort;
import org.springframework.stereotype.Component;

@Component
public class GetCharacterQry {

    private final CharacterPort characterPort;

    public GetCharacterQry(CharacterPort characterPort) {
        this.characterPort = characterPort;
    }

    public Character execute(String name) {
        return characterPort.getCharacter(name);
    }
}

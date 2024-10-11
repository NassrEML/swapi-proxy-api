package com.nassreml.swapi.proxy.api.core.domain.ports;

import com.nassreml.swapi.proxy.api.core.domain.Character;

public interface CharacterPort {
    Character getCharacter(String name);
}

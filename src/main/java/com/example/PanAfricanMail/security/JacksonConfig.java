package com.example.PanAfricanMail.security;

import com.fasterxml.jackson.core.io.CharacterEscapes;
import com.fasterxml.jackson.core.io.SerializedString;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JacksonConfig {
    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.getFactory().setCharacterEscapes(new CharacterEscapes() {
            private final int[] esc = CharacterEscapes.standardAsciiEscapesForJSON();
            {
                esc['<'] = CharacterEscapes.ESCAPE_NONE;
                esc['>'] = CharacterEscapes.ESCAPE_NONE;
            }
            @Override
            public int[] getEscapeCodesForAscii() {
                return esc;
            }
            @Override
            public SerializedString getEscapeSequence(int ch) {
                return null;
            }
        });
        return mapper;
    }
}

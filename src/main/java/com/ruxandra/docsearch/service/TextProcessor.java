package com.ruxandra.docsearch.service;

import org.springframework.stereotype.Component;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

@Component
public class TextProcessor {
    public List<String> tokenize(String text) {
        if (text == null || text.isBlank()) {
            return List.of();
        }

        return Arrays.stream(text.toLowerCase(Locale.ROOT).split("[^a-z0-9]+"))
                .filter(token -> !token.isBlank())
                .toList();
    }
}

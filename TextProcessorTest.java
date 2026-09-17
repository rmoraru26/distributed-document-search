package com.ruxandra.docsearch.service;

import org.junit.jupiter.api.Test;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

class TextProcessorTest {
    private final TextProcessor textProcessor = new TextProcessor();

    @Test
    void tokenizesTextAndNormalizesCase() {
        List<String> tokens = textProcessor.tokenize("Search engines, SEARCH documents!");
        assertThat(tokens).containsExactly("search", "engines", "search", "documents");
    }

    @Test
    void returnsEmptyListForBlankText() {
        assertThat(textProcessor.tokenize("   ")).isEmpty();
    }
}

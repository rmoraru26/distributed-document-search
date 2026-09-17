package com.ruxandra.docsearch.service;

import com.ruxandra.docsearch.dto.SearchResult;
import com.ruxandra.docsearch.model.Document;
import com.ruxandra.docsearch.repository.DocumentRepository;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class SearchService {
    private final DocumentRepository documentRepository;
    private final TextProcessor textProcessor;

    public SearchService(DocumentRepository documentRepository, TextProcessor textProcessor) {
        this.documentRepository = documentRepository;
        this.textProcessor = textProcessor;
    }

    public List<SearchResult> search(String query) {
        List<String> queryTerms = textProcessor.tokenize(query);
        if (queryTerms.isEmpty()) {
            return List.of();
        }

        List<Document> documents = documentRepository.findAll();
        Map<Document, List<String>> tokensByDocument = new HashMap<>();
        for (Document document : documents) {
            tokensByDocument.put(document, textProcessor.tokenize(document.getContent()));
        }

        List<SearchResult> results = new ArrayList<>();
        Set<String> uniqueQueryTerms = new HashSet<>(queryTerms);

        for (Document document : documents) {
            List<String> tokens = tokensByDocument.get(document);
            double score = 0.0;

            for (String term : uniqueQueryTerms) {
                long occurrences = tokens.stream().filter(term::equals).count();
                if (occurrences == 0) {
                    continue;
                }

                double termFrequency = (double) occurrences / tokens.size();
                long documentFrequency = tokensByDocument.values().stream()
                        .filter(documentTokens -> documentTokens.contains(term))
                        .count();
                double inverseDocumentFrequency =
                        Math.log((documents.size() + 1.0) / (documentFrequency + 1.0)) + 1.0;
                score += termFrequency * inverseDocumentFrequency;
            }

            if (score > 0.0) {
                results.add(new SearchResult(
                        document.getId(),
                        document.getFilename(),
                        score,
                        createPreview(document.getContent(), uniqueQueryTerms)
                ));
            }
        }

        return results.stream()
                .sorted((first, second) -> Double.compare(second.score(), first.score()))
                .toList();
    }

    private String createPreview(String content, Set<String> queryTerms) {
        String lowerContent = content.toLowerCase();
        int matchIndex = queryTerms.stream()
                .map(lowerContent::indexOf)
                .filter(index -> index >= 0)
                .min(Integer::compareTo)
                .orElse(0);

        int start = Math.max(0, matchIndex - 60);
        int end = Math.min(content.length(), matchIndex + 140);
        return (start > 0 ? "..." : "") + content.substring(start, end) + (end < content.length() ? "..." : "");
    }
}

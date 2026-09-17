package com.ruxandra.docsearch.dto;

public record SearchResult(Long documentId, String filename, double score, String preview) {
}

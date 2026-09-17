package com.ruxandra.docsearch.dto;

import com.ruxandra.docsearch.model.Document;
import java.time.Instant;

public record DocumentResponse(Long id, String filename, Instant uploadedAt) {
    public static DocumentResponse from(Document document) {
        return new DocumentResponse(document.getId(), document.getFilename(), document.getUploadedAt());
    }
}

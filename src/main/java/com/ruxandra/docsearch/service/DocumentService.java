package com.ruxandra.docsearch.service;

import com.ruxandra.docsearch.dto.DocumentResponse;
import com.ruxandra.docsearch.model.Document;
import com.ruxandra.docsearch.repository.DocumentRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
public class DocumentService {
    private final DocumentRepository documentRepository;

    public DocumentService(DocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
    }

    public DocumentResponse upload(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("The uploaded file is empty.");
        }
        String filename = file.getOriginalFilename() == null ? "document.txt" : file.getOriginalFilename();
        if (!filename.toLowerCase().endsWith(".txt")) {
            throw new IllegalArgumentException("Only .txt files are supported in this version.");
        }

        String content = new String(file.getBytes(), StandardCharsets.UTF_8);
        Document savedDocument = documentRepository.save(new Document(filename, content));
        return DocumentResponse.from(savedDocument);
    }

    public List<DocumentResponse> findAll() {
        return documentRepository.findAll().stream().map(DocumentResponse::from).toList();
    }

    public void delete(Long id) {
        if (!documentRepository.existsById(id)) {
            throw new IllegalArgumentException("Document with id " + id + " does not exist.");
        }
        documentRepository.deleteById(id);
    }
}

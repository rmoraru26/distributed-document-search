package com.ruxandra.docsearch.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;

@Entity
@Table(name = "documents")
public class Document {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String filename;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(nullable = false)
    private Instant uploadedAt;

    protected Document() {
    }

    public Document(String filename, String content) {
        this.filename = filename;
        this.content = content;
        this.uploadedAt = Instant.now();
    }

    public Long getId() { return id; }
    public String getFilename() { return filename; }
    public String getContent() { return content; }
    public Instant getUploadedAt() { return uploadedAt; }
}

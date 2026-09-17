package com.ruxandra.docsearch.repository;

import com.ruxandra.docsearch.model.Document;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentRepository extends JpaRepository<Document, Long> {
}

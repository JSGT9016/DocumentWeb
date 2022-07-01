package com.jos.document.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jos.document.entities.Document;

public interface DocumentRepository extends JpaRepository<Document,Long>{
}

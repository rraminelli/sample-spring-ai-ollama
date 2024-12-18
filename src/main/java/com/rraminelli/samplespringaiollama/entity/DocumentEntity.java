//package com.rraminelli.samplespringaiollama.entity;
//
//import jakarta.persistence.*;
//import org.hibernate.annotations.GenericGenerator;
//import org.hibernate.annotations.JdbcTypeCode;
//import org.hibernate.type.SqlTypes;
//
//import java.time.Instant;
//import java.util.Map;
//import java.util.UUID;
//
//@Entity
//@Table(name = "documents")
//public class DocumentEntity {
//    @Id
//    @GeneratedValue(generator = "uuid2")
//    @GenericGenerator(name = "uuid2", strategy = "uuid2")
//    @Column(columnDefinition = "UUID")
//    private UUID id;
//
//    @Column(name = "document_name", unique = true, nullable = false)
//    private String documentName;
//
//    @Column(name = "created_at")
//    private Instant createdAt;
//
//    @Column(name = "updated_at")
//    private Instant updatedAt;
//
//    @JdbcTypeCode(SqlTypes.JSON)
//    @Column(columnDefinition = "jsonb")
//    private Map<String, Object> metadata;
//
//    public DocumentEntity() {}
//
//    @PrePersist
//    protected void onCreate() {
//        createdAt = Instant.now();
//        updatedAt = Instant.now();
//    }
//
//    @PreUpdate
//    protected void onUpdate() {
//        updatedAt = Instant.now();
//    }
//
//    public UUID getId() {
//        return id;
//    }
//
//    public void setId(UUID id) {
//        this.id = id;
//    }
//
//    public String getDocumentName() {
//        return documentName;
//    }
//
//    public void setDocumentName(String documentName) {
//        this.documentName = documentName;
//    }
//
//    public Instant getCreatedAt() {
//        return createdAt;
//    }
//
//    public void setCreatedAt(Instant createdAt) {
//        this.createdAt = createdAt;
//    }
//
//    public Instant getUpdatedAt() {
//        return updatedAt;
//    }
//
//    public void setUpdatedAt(Instant updatedAt) {
//        this.updatedAt = updatedAt;
//    }
//
//    public Map<String, Object> getMetadata() {
//        return metadata;
//    }
//
//    public void setMetadata(Map<String, Object> metadata) {
//        this.metadata = metadata;
//    }
//}
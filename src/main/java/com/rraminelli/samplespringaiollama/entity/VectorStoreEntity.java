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
//@Table(name = "vector_store")
//public class VectorStoreEntity {
//
//
//
//    @Id
//    @GeneratedValue(generator = "uuid2")
//    @GenericGenerator(name = "uuid2", strategy = "uuid2")
//    @Column(columnDefinition = "UUID")
//    private UUID id;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "document_id", nullable = false)
//    private DocumentEntity document;
//
//    @Column(name = "content")
//    private String content;
//
//    @JdbcTypeCode(SqlTypes.JSON)
//    @Column(columnDefinition = "jsonb")
//    private Map<String, Object> metadata;
//
//    @Column(name = "embedding", columnDefinition = "vector(1024)")
//    private float[] embedding;
//
//    @Column(name = "chunk_index")
//    private Integer chunkIndex;
//
//    @Column(name = "created_at")
//    private Instant createdAt;
//
//    @Column(name = "updated_at")
//    private Instant updatedAt;
//
//    public VectorStoreEntity() {}
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
//    public DocumentEntity getDocument() {
//        return document;
//    }
//
//    public void setDocument(DocumentEntity document) {
//        this.document = document;
//    }
//
//    public String getContent() {
//        return content;
//    }
//
//    public void setContent(String content) {
//        this.content = content;
//    }
//
//    public Map<String, Object> getMetadata() {
//        return metadata;
//    }
//
//    public void setMetadata(Map<String, Object> metadata) {
//        this.metadata = metadata;
//    }
//
//    public float[] getEmbedding() {
//        return embedding;
//    }
//
//    public void setEmbedding(float[] embedding) {
//        this.embedding = embedding;
//    }
//
//    public Integer getChunkIndex() {
//        return chunkIndex;
//    }
//
//    public void setChunkIndex(Integer chunkIndex) {
//        this.chunkIndex = chunkIndex;
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
//}
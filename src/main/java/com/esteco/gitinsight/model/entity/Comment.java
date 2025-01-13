package com.esteco.gitinsight.model.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;
@Entity
public class Comment {
    @Id
    private String id;
    private String body;
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "issue_id", nullable = false)
    private Issue issue;

    public String getId() {
        return id;
    }

    public Comment() {
        this(UUID.randomUUID().toString());
    }

    public Comment(String id) {
        this.id = id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setIssue(Issue issue) {
        this.issue = issue;
    }
}

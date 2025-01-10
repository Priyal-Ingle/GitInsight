package com.esteco.gitinsight.model.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

import static java.util.UUID.randomUUID;

@Entity
public class PullRequest {
    @Id

    private String id;
    private String title;
    private String body;
    private String url;
    private long fileChangeCount;
    private long commitCount;
    private LocalDateTime createdAt;
    private LocalDateTime closedAt;
    private boolean closed;


    @OneToMany(cascade = CascadeType.ALL)
    private List<Commit> CommitId;

    @OneToOne(cascade = CascadeType.ALL)
    private Author author;


    public PullRequest() {
        this(randomUUID().toString());
    }


    public PullRequest(String id) {
        this.id= id;
    }
    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public long getFileChangeCount() {
        return fileChangeCount;
    }

    public void setFileChangeCount(long fileChangeCount) {
        this.fileChangeCount = fileChangeCount;
    }

    public long getCommitCount() {
        return commitCount;
    }

    public void setCommitCount(long commitCount) {
        this.commitCount = commitCount;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getClosedAt() {
        return closedAt;
    }

    public void setClosedAt(LocalDateTime closedAt) {
        this.closedAt = closedAt;
    }

    public boolean isClosed() {
        return closed;
    }

    public void setClosed(boolean closed) {
        this.closed = closed;
    }
}

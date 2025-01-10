package com.esteco.gitinsight.model.entity;


import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

import static java.util.UUID.randomUUID;

@Entity

public class Issue {
    @Id

    private String id;
    private String title;
    private long totalComment;
    private String url;
    private String body;
    private LocalDateTime closedAt;
    private long prCount;
    private boolean closed;
    private LocalDateTime createdAt;
    private long labelCount;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Label> labels;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Comment> comments;




    @OneToOne(cascade = CascadeType.ALL)
    private Author author;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Author> assignees;
//    Yet to connect to Pull Request



    public Issue(){
        this(randomUUID().toString());
    }

    public Issue(String id) {
        this.id = id;
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

    public long getTotalComment() {
        return totalComment;
    }

    public void setTotalComment(long totalComment) {
        this.totalComment = totalComment;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public LocalDateTime getClosedAt() {
        return closedAt;
    }

    public void setClosedAt(LocalDateTime closedAt) {
        this.closedAt = closedAt;
    }

    public long getPrCount() {
        return prCount;
    }

    public void setPrCount(long prCount) {
        this.prCount = prCount;
    }

    public boolean isClosed() {
        return closed;
    }

    public void setClosed(boolean closed) {
        this.closed = closed;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public long getLabelCount() {
        return labelCount;
    }

    public void setLabelCount(long labelCount) {
        this.labelCount = labelCount;
    }

    public List<Label> getLabels() {
        return labels;
    }

    public void setLabels(List<Label> labels) {
        this.labels = labels;
    }
}

package com.esteco.gitinsight.model.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "COMMENT")
public class
Comment {
    @Id
    private String id;
    @Column(length = 10000)
    private String body;
    private LocalDateTime createdAt;
//    :TODO add comment associated user
    @ManyToOne(cascade = CascadeType.ALL)
    private GitUser commentAuthor;

    public GitUser getCommentAuthor() {
        return commentAuthor;
    }

    public void setCommentAuthor(GitUser gitUser) {
        this.commentAuthor = gitUser;
    }

    //    *********************getter setter start ********************
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

}

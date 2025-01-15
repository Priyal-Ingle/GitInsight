package com.esteco.gitinsight.model.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static java.util.UUID.randomUUID;

@Entity
public class PullRequest {
    @Id
    private String id;
    private String title;
    private String body;
    private String url;
    private LocalDateTime createdAt;
    private LocalDateTime closedAt;
    @OneToMany(
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    private List<Commit> commits=new ArrayList<>();
    //    :TODO add comment associated user
//    @OneToOne(cascade = CascadeType.ALL)
//    private GitUser gitUser;



//    *********************getter setter start ********************
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public PullRequest() {
        this(randomUUID().toString());
    }

    public PullRequest(String id) {
        this.id= id;
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

    public List<Commit> getCommits() {
        return commits;
    }

    public void setCommits(List<Commit> commits) {
        this.commits = commits;
    }


}

package com.esteco.gitinsight.model.entity;

import jakarta.persistence.*;

import java.util.List;
import java.util.UUID;

@Entity
public class Commit {
    @Id

    private String id;

    public PullRequest getPullRequest() {
        return pullRequest;
    }

    public void setPullRequest(PullRequest pullRequest) {
        this.pullRequest = pullRequest;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    private String url;


    @ManyToOne(cascade = CascadeType.ALL)
    private PullRequest pullRequest;

    @ManyToOne(cascade = CascadeType.ALL)
    private Author author;

    public Commit() {
        this(UUID.randomUUID().toString());
    }

    public Commit(String id) {
        this.id = id;
    }


    public String getId() {
        return id;
    }



    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}

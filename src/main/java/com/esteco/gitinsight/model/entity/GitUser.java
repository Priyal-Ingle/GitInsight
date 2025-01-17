package com.esteco.gitinsight.model.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import static java.util.UUID.randomUUID;

@Entity
@Table(name = "GITUSER")
public class GitUser {
    @Id
    private String id;
    private String username;
    private String url;

//    *********************getter setter start ********************
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getLogin() {
        return username;
    }

    public void setId(String id) {
        this.id = id;
    }

    public GitUser() {
        this(randomUUID().toString());
    }

    public GitUser(String id) {
        this.id= id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getId() {
        return id;
    }
}

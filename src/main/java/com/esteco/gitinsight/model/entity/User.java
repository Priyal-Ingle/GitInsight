package com.esteco.gitinsight.model.entity;


import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

import static java.util.UUID.randomUUID;

@Entity
public class User {

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

    public User() {
        this(randomUUID().toString());
    }

    public User(String username) {
        this.username= username;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}

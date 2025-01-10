package com.esteco.gitinsight.model.entity;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.List;

import static java.util.UUID.randomUUID;

@Entity

public class Author {

    @Id

    private String username;
    private String url;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Label> labels;

    public String getLogin() {
        return username;
    }
    public Author() {
        this(randomUUID().toString());
    }

    public Author(String username) {
        this.username= username;
    }


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}

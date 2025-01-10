package com.esteco.gitinsight.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.UUID;

@Entity
public class Commit {
    @Id

    private String id;
    private String url;


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

package com.esteco.gitinsight.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.UUID;

@Entity
@Table(name = "COMMIT")
public class Commit {
    @Id
    private String id;
    private String url;

//    *********************getter setter start ********************
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Commit(String id) {
        this.id=id;
    }

    public Commit(){
        this(UUID.randomUUID().toString());
    }

}

package com.esteco.gitinsight.model.entity;

import jakarta.persistence.*;

import java.util.UUID;

import static java.util.UUID.randomUUID;

@Entity

public class Language {
    @Id

    private String id;
    private String name;
    private String color;

    @ManyToOne(
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "git_repo_id", nullable = false)
    private GitRepo git_repo;

    public Language() {
        this(randomUUID().toString());
    }

    public Language(String name, String color, GitRepo git_repo) {
        this(UUID.randomUUID().toString());
        this.name = name;
        this.color = color;
        this.git_repo = git_repo;
    }

    public Language(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}

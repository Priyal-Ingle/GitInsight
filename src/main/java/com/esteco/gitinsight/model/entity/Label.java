package com.esteco.gitinsight.model.entity;

import com.esteco.gitinsight.github.dto.Repository;
import jakarta.persistence.*;

import java.util.List;
import java.util.UUID;

@Entity
public class Label {
    @Id
    private String id;
    private String name;
    private String color;

    @ManyToOne(
            fetch = FetchType.LAZY
    )
    private GitRepo gitRepo;

    public void setGitRepo(GitRepo gitRepo) {
        this.gitRepo = gitRepo;
    }
    public GitRepo getGitRepo() {
        return gitRepo;
    }

    public Label(String id) {
        this.id = id;
    }

    public Label(String name, String color) {
        this(UUID.randomUUID().toString());
        this.name = name;
        this.color = color;
    }

    public Label() {
        this(UUID.randomUUID().toString());
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

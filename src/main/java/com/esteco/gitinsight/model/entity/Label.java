package com.esteco.gitinsight.model.entity;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
public class Label {
    @Id
    private String id;
    private String name;
    private String color;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "git_repo_id", nullable = false)
    private GitRepo git_repo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "issue_id", nullable = false)
    private Issue issue;


//**************************** getter and setter for issue**************************************
    public Issue getIssue() {
        return issue;
    }

    public void setIssue(Issue issue) {
        this.issue = issue;
    }





    public void setGit_repo(GitRepo git_repo) {
        this.git_repo = git_repo;
    }
    public GitRepo getGit_repo() {
        return git_repo;
    }

    public Label(String id) {
        this.id = id;
    }

    public Label(String name, String color, GitRepo git_repo) {
        this(UUID.randomUUID().toString());
        this.name = name;
        this.color = color;
        this.git_repo = git_repo;
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

    @Override
    public String toString() {
        return "Label [id=" + id + ", name=" + name + ", color=" + color + "]";
    }

}

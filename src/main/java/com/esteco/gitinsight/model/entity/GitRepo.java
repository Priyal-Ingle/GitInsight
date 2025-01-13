package com.esteco.gitinsight.model.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
public class GitRepo {
    @Id
    private String id;


    private String url;
    private String repoOwner;
    private String repoName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private boolean privateRepository;


    /*@OneToMany(
            cascade = CascadeType.ALL,
            mappedBy = "git_repo",
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    private List<Label> labels = new ArrayList<>();*/

    @OneToMany(
            cascade = CascadeType.ALL,
            mappedBy = "git_repo",
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    private List<Language> languages = new ArrayList<>();

    /*@OneToMany(
            cascade = CascadeType.ALL,
            mappedBy = "git_repo",
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    private List<Issue> issues = new ArrayList<>();*/



    public GitRepo() {
        this(UUID.randomUUID().toString());
    }

    public GitRepo(String id) {
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

    public String getRepoOwner() {
        return repoOwner;
    }

    public void setRepoOwner(String repoOwner) {
        this.repoOwner = repoOwner;
    }

    public String getRepoName() {
        return repoName;
    }

    public void setRepoName(String repoName) {
        this.repoName = repoName;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }



    public boolean isPrivateRepository() {
        return privateRepository;
    }

    public void setPrivateRepository(boolean privateRepository) {
        this.privateRepository = privateRepository;
    }

//******************   Issues getter setter **************************

    /*public List<Issue> getIssues() {
        return issues;
    }

    public void setIssues(List<Issue> issues) {
        this.issues.addAll(issues);
    }*/

//******************   languages getter setter **************************
    public List<Language> getLanguages() {
        return languages;
    }

    public void setLanguages(List<Language> languages) {
        this.languages.addAll(languages);
    }


//******************   labels getter setter **************************
    /*public List<Label> getLabels() {
        return labels;
    }

    public void setLabels(List<Label> labelsList) {
        this.labels.addAll(labelsList);
    }*/

    /*@Override
    public String toString() {
        String labelsInfo;
        try {
            // Attempt to access the labels list
            labelsInfo = (labels == null || labels.isEmpty())
                    ? "Labels not loaded or empty"
                    : labels.toString();
        } catch (org.hibernate.LazyInitializationException e) {
            // Handle the case where labels are not initialized
            labelsInfo = "Labels not initialized (LazyInitializationException)";
        }

        return "GitRepo{" +
                "id='" + id + '\'' +
                ", url='" + url + '\'' +
                ", repoOwner='" + repoOwner + '\'' +
                ", repoName='" + repoName + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", privateRepository=" + privateRepository +
                ", labels=" + labelsInfo +
                '}';
    }*/



}

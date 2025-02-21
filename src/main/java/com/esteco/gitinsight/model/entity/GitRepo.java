package com.esteco.gitinsight.model.entity;

import jakarta.persistence.*;
import jakarta.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "GITREPO")
public class GitRepo {
    @Id
    private String id;
    private String url;
    private String repoOwner;
    private String repoName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private boolean privateRepository;
    private String endCursor = "";

    @ManyToMany(
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    private List<Language> languages = new ArrayList<>();

    @OneToMany(
            cascade = CascadeType.ALL,
//            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    private List<Issue> issues = new ArrayList<>();

//    *********************getter setter start ********************

    public GitRepo() {
        this(UUID.randomUUID().toString());
    }
    public GitRepo(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getEndCursor() {
        return endCursor;
    }

    public void setEndCursor(String endCursor) {
        this.endCursor = endCursor;
    }

//    public List<Label> getLabels() {
//        return labels;
//    }
//
//    public void setLabels(List<Label> labels) {
//        this.labels = labels;
//    }
//
//    public void setLabel(Label label) {
//        this.labels.add(label);
//    }

    public List<Language> getLanguages() {
        return languages;
    }

    public void setLanguages(List<Language> languages) {
        this.languages = languages;
    }

    public void setLanguage(Language language) {
        this.languages.add(language);
    }

    public List<Issue> getIssues() {
        return issues;
    }

    public void setIssues(List<Issue> issues) {
        if(this.issues.isEmpty()){
            this.issues = issues;
            System.out.println("if");
        }
        else{
            this.issues.addAll(issues);
            System.out.println("else");

        }
    }

}

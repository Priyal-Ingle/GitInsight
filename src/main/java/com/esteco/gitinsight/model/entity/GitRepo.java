package com.esteco.gitinsight.model.entity;

import com.esteco.gitinsight.model.repository.LabelRepository;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
public class GitRepo {
    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;
    private String url;
    private String repoOwner;
    private String repoName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private long forkedCount;
    private boolean privateRepository;
    private long labelCount;
    private long languageCount;
    private long issuesCount;


    @OneToMany(
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JoinColumn(name = "label_id", referencedColumnName = "id")
    private List<Label> labels = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    private List<Language> languages;


    public List<Label> getLabels() {
        return labels;
    }

    public void removeLabel(Label label) {
        this.labels.remove(label);
        label.setGitRepo(null);
    }

    public void setLabel(Label label) {
        labels.add(label);
        label.setGitRepo(this);
    }

    public GitRepo() {
        this(UUID.randomUUID().toString());
    }

    public GitRepo(String id) {
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

    public long getForkedCount() {
        return forkedCount;
    }

    public void setForkedCount(long forkedCount) {
        this.forkedCount = forkedCount;
    }

    public boolean isPrivateRepository() {
        return privateRepository;
    }

    public void setPrivateRepository(boolean privateRepository) {
        this.privateRepository = privateRepository;
    }

    public long getLabelCount() {
        return labelCount;
    }

    public void setLabelCount(long labelCount) {
        this.labelCount = labelCount;
    }

    public long getLanguageCount() {
        return languageCount;
    }

    public void setLanguageCount(long languageCount) {
        this.languageCount = languageCount;
    }

    public long getIssuesCount() {
        return issuesCount;
    }

    public void setIssuesCount(long issuesCount) {
        this.issuesCount = issuesCount;
    }
}

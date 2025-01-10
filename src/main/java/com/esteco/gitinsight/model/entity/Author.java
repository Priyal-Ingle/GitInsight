package com.esteco.gitinsight.model.entity;


import jakarta.persistence.*;

import java.util.List;

import static java.util.UUID.randomUUID;

@Entity

public class Author {

    @Id

    private String username;
    private String url;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Label> labels;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Issue> createdIssues;

    @ManyToMany()
    private List<Issue> assignedIssues;

    public List<PullRequest> getCreatedPullRequests() {
        return createdPullRequests;
    }

    public void setCreatedPullRequests(List<PullRequest> createdPullRequests) {
        this.createdPullRequests = createdPullRequests;
    }

    public List<Commit> getCreatedCommits() {
        return createdCommits;
    }

    public void setCreatedCommits(List<Commit> createdCommits) {
        this.createdCommits = createdCommits;
    }

    public List<Issue> getAssignedIssues() {
        return assignedIssues;
    }

    public void setAssignedIssues(List<Issue> assignedIssues) {
        this.assignedIssues = assignedIssues;
    }

    public List<Issue> getCreatedIssues() {
        return createdIssues;
    }

    public void setCreatedIssues(List<Issue> createdIssues) {
        this.createdIssues = createdIssues;
    }

    public List<Label> getLabels() {
        return labels;
    }

    public void setLabels(List<Label> labels) {
        this.labels = labels;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @OneToMany(cascade = CascadeType.ALL)
    private List<Commit> createdCommits;

    @OneToMany(cascade = CascadeType.ALL)
    private List<PullRequest> createdPullRequests;








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

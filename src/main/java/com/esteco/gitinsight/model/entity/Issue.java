package com.esteco.gitinsight.model.entity;


import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static java.util.UUID.randomUUID;

@Entity

public class Issue {
    @Id
    private String id;
    private String title;
    private long totalComment;
    private String url;
    private String body;
    private LocalDateTime closedAt;
    private long prCount;
    private boolean closed;
    private LocalDateTime createdAt;
    private long labelCount;


    @OneToMany(
            cascade = CascadeType.ALL,
            mappedBy = "issue",
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    private List<Label> labels = new ArrayList<>();

    @OneToMany(
            cascade = CascadeType.ALL,
            mappedBy = "issue",
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    private List<Comment> comments = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "git_repo_id", nullable = false)
    private GitRepo git_repo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_username", nullable = false)
    private Author author;

    public List<Author> getAssignees() {
        return assignees;
    }

    public void setAssignees(List<Author> assignees) {
        this.assignees = assignees;
    }

    public List<PullRequest> getAssociatedPullRequests() {
        return associatedPullRequests;
    }

    public void setAssociatedPullRequests(List<PullRequest> associatedPullRequests) {
        this.associatedPullRequests = associatedPullRequests;
    }

    @ManyToMany
    @JoinTable(
            name = "issue_assignees",
            joinColumns = @JoinColumn(name = "issue_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id")
    )
    private List<Author> assignees;


    @ManyToMany(mappedBy = "associatedIssues")
    private List<PullRequest> associatedPullRequests;


//    *********************getter setter start ********************


    public Issue(){
        this(randomUUID().toString());
    }

    public Issue(String id) {
        this.id = id;
    }

/*    public Issue(String title, long totalComment, String url, String body,
                 LocalDateTime closedAt, long prCount, boolean closed,
                 LocalDateTime createdAt, long labelCount, List<Label> labels, List<Comment> comments,
                 GitRepo git_repo, Author author, List<Author> assignees, List<PullRequest> associatedPullRequests) {
        this.title = title;
        this.totalComment = totalComment;
        this.url = url;
        this.body = body;
        this.closedAt = closedAt;
        this.prCount = prCount;
        this.closed = closed;
        this.createdAt = createdAt;
        this.labelCount = labelCount;
        this.labels = labels;
        this.comments = comments;
        this.git_repo = git_repo;
        this.author = author;
//        this.assignees = assignees;
//        this.associatedPullRequests = associatedPullRequests;
    }*/



    /*public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }*/

    /*public GitRepo getGitRepo() {
        return git_repo;
    }

    public void setGitRepo(GitRepo gitRepo) {
        this.git_repo = gitRepo;
    }*/

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

//    public List<Author> getAssignees() {
//        return assignees;
//    }
//
//    public void setAssignees(List<Author> assignees) {
//        this.assignees = assignees;
//    }
//
//    public List<PullRequest> getAssociatedPullRequests() {
//        return associatedPullRequests;
//    }
//
//    public void setAssociatedPullRequests(List<PullRequest> associatedPullRequests) {
//        this.associatedPullRequests = associatedPullRequests;
//    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getTotalComment() {
        return totalComment;
    }

    public void setTotalComment(long totalComment) {
        this.totalComment = totalComment;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public LocalDateTime getClosedAt() {
        return closedAt;
    }

    public void setClosedAt(LocalDateTime closedAt) {
        this.closedAt = closedAt;
    }

    public long getPrCount() {
        return prCount;
    }

    public void setPrCount(long prCount) {
        this.prCount = prCount;
    }

    public boolean isClosed() {
        return closed;
    }

    public void setClosed(boolean closed) {
        this.closed = closed;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public long getLabelCount() {
        return labelCount;
    }

    public void setLabelCount(long labelCount) {
        this.labelCount = labelCount;
    }


//**********************getter and setter for labels************************************
    public List<Label> getLabels() {
        return labels;
    }

    public void setLabels(List<Label> labels) {
        this.labels.addAll(labels);
    }

    public void setGitRepo(GitRepo git_repo) {
        this.git_repo = git_repo;
    }
}

package com.esteco.gitinsight.response;

import com.esteco.gitinsight.github.dto.*;
import com.esteco.gitinsight.github.dto.Comment;
import com.esteco.gitinsight.github.dto.GitUser;
import com.esteco.gitinsight.model.entity.*;
import com.esteco.gitinsight.model.repository.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class PersistResponse {

    private final LanguageRepository languageRepository;
    private final CommentRepository commentRepository;
    private final IssueRepository issueRepository;
    private final PullRequestRepository pullRequestRepository;
    private final GitRepository gitRepository;
    private final AuthorRepository authorRepository;
    private final LabelRepository labelRepository;

    public PersistResponse(GitRepository gitRepository, LanguageRepository languageRepository, CommentRepository commentRepository, IssueRepository issueRepository, PullRequestRepository pullRequestRepository, AuthorRepository authorRepository, LabelRepository labelRepository) {
        this.gitRepository = gitRepository;
        this.languageRepository = languageRepository;
        this.commentRepository = commentRepository;
        this.issueRepository = issueRepository;
        this.pullRequestRepository = pullRequestRepository;
        this.authorRepository = authorRepository;
        this.labelRepository = labelRepository;
    }

    public void storeLanguages(File file) throws IOException {
        ResponseJson responseJson = new ResponseJson();
        GitRepositoryData repositoryData = responseJson.getRepositoryData(file);

        GitRepo gitRepo = new GitRepo(repositoryData.id());
        gitRepo.setUrl(repositoryData.url());
        gitRepo.setCreatedAt(repositoryData.createdAt());
        gitRepo.setUpdatedAt(repositoryData.updatedAt());
        gitRepo.setPrivateRepository(false);

        Map<String, List<CodeLanguage>> codeLanguages = responseJson.getLanguages(file);
        List<CodeLanguage> languageList = codeLanguages.get(repositoryData.id());
        List<Language> storedLanguages = new ArrayList<>();
        for (CodeLanguage codeLanguage : languageList) {
            Optional<Language> fetchedLanguage = languageRepository.findById(codeLanguage.id());
            if(fetchedLanguage.isEmpty()) {
                Language language = storeLanguage(codeLanguage);
                storedLanguages.add(language);
            }else{
                storedLanguages.add(fetchedLanguage.get());
            }
        }
        gitRepo.setLanguages(storedLanguages);
        gitRepository.save(gitRepo);
    }

    private Language storeLanguage(CodeLanguage language) {
        Language entityLanguage = new Language(language.id());
        entityLanguage.setName(language.name());
        entityLanguage.setColor(language.color());
        languageRepository.save(entityLanguage);
        return entityLanguage;
    }

    public void storeComments(File file) throws IOException {
        ResponseJson responseJson = new ResponseJson();
        Map<String, List<Comment>> commentData = responseJson.getCommentData(file);
        List<com.esteco.gitinsight.model.entity.Comment> comments = new ArrayList<>();
        commentData.forEach((issueId, value) -> {
            Issue issue = new Issue(issueId);
            value.forEach(comment -> storeComment(comment, comments));
            issueRepository.save(issue);
        });
    }

    private void storeComment(Comment comment, List<com.esteco.gitinsight.model.entity.Comment> comments) {

        Optional<com.esteco.gitinsight.model.entity.Comment> byId = commentRepository.findById(comment.id());
        if (byId.isPresent()) {
            com.esteco.gitinsight.model.entity.Comment entityComment = byId.get();
            entityComment.setBody(comment.body());
            entityComment.setCreatedAt(comment.createdAt());
            commentRepository.save(entityComment);
            comments.add(entityComment);
        }
    }

    public void storeIssueAuthor(File file) throws IOException {
        Map<String, GitUser> issueAuthor = new ResponseJson().getIssueAuthor(file);
        issueAuthor.forEach(this::storeAuthorAndIssue);
    }

    private void storeAuthorAndIssue(String issueId, GitUser value) {
        Issue issue = new Issue(issueId);
        com.esteco.gitinsight.model.entity.GitUser creator = new com.esteco.gitinsight.model.entity.GitUser();
        creator.setUsername(value.login());
        issue.setAuthor(creator);
        issueRepository.save(issue);
    }

    public void storeCommentAuthor(File file) throws IOException {
        Map<String, GitUser> issueCommentAuthor = new ResponseJson().getIssueCommentAuthor(file);
        issueCommentAuthor.forEach((commentId, value) -> {
            Optional<com.esteco.gitinsight.model.entity.GitUser> byUsername = authorRepository.findByUsername(value.login());
            com.esteco.gitinsight.model.entity.Comment comment = new com.esteco.gitinsight.model.entity.Comment();
            comment.setId(commentId);
            if (byUsername.isEmpty()) {
                com.esteco.gitinsight.model.entity.GitUser creator = new com.esteco.gitinsight.model.entity.GitUser();
                creator.setUsername(value.login());
                creator.setUrl(value.url());
                comment.setCommentAuthor(creator);
            }else{
                comment.setCommentAuthor(byUsername.get());
            }
            commentRepository.save(comment);

        });

    }

    public void storePullRequestCommits(File file) throws IOException {
        Map<String, List<PRCommit>> pullRequestCommits = new ResponseJson().getIssueCommits(file);
        pullRequestCommits.forEach((prId, value) -> {
            PullRequest pullRequest = new PullRequest(prId);
            List<Commit> commits = new ArrayList<>();
            value.forEach(commit -> {
                Commit entityCommit = new Commit(commit.id());
                entityCommit.setUrl(commit.url());
                commits.add(entityCommit);
            });
            pullRequest.setCommits(commits);
            pullRequestRepository.save(pullRequest);

        });
    }

    public void storePullRequests(File file) throws IOException {
        Map<String, List<GitPullRequest>> closedPullRequests = new ResponseJson().getClosedPullRequests(file);
        closedPullRequests.forEach((issueId, value) -> {
            Issue issue = new Issue(issueId);
            List<PullRequest> pullRequests = new ArrayList<>();
            value.forEach(pullRequest -> {
                Optional<PullRequest> byId = pullRequestRepository.findById(pullRequest.id());
                if (byId.isEmpty()) {
                    PullRequest PR = new PullRequest(pullRequest.id());
                    PR.setUrl(pullRequest.url());
                    PR.setBody(pullRequest.body());
                    PR.setCreatedAt(pullRequest.createdAt());
                    PR.setClosedAt(pullRequest.closedAt());
                    PR.setTitle(pullRequest.title());
                    pullRequests.add(PR);
                }
                else{
                    PullRequest PR = byId.get();
                    pullRequests.add(PR);
                }
            });
            issue.setPullRequests(pullRequests);
            issueRepository.save(issue);

        });
    }

    public void storeIssueAssignees(File file) throws IOException {
        Map<String, List<GitUser>> issueAssignees = new ResponseJson().getIssueAssignees(file);
        issueAssignees.forEach((issueId, value) -> {
            Issue issue = new Issue(issueId);
            List<com.esteco.gitinsight.model.entity.GitUser> assignees = new ArrayList<>();
            value.forEach(assignee -> {
                Optional<com.esteco.gitinsight.model.entity.GitUser> byUsername = authorRepository.findByUsername(assignee.login());
                if (byUsername.isEmpty()) {
                    com.esteco.gitinsight.model.entity.GitUser assignee1 = new com.esteco.gitinsight.model.entity.GitUser();
                    assignee1.setUsername(assignee.login());
                    assignee1.setUrl(assignee.url());
                    assignees.add(assignee1);
                }
                else{
                    assignees.add(byUsername.get());
                }
            });
            issue.setAssignees(assignees);
            issueRepository.save(issue);
        });
    }

    public void storeIssueLabels(File file) throws IOException {
        Map<String, List<IssueLabel>> labels = new ResponseJson().getLabels(file);
        labels.forEach((issueId, value) -> {
            Issue issue = new Issue(issueId);
            List<Label> entityLabels = new ArrayList<>();
            value.forEach(entityLabel -> {
                Optional<Label> byId = labelRepository.findById(entityLabel.id());
                if (byId.isEmpty()) {
                    Label label = new Label(entityLabel.id());
                    label.setName(entityLabel.name());
                    label.setColor(entityLabel.color());
                    entityLabels.add(label);
                }
                else{
                    entityLabels.add(byId.get());
                }
            });
            issue.setLabels(entityLabels);
            issueRepository.save(issue);
        });

    }

    public void storeIssues(File file) throws IOException {
        Map<String, List<GitIssue>> issues = new ResponseJson().getIssues(file);
        issues.forEach((repoId, value) -> {
            GitRepo gitRepo = new GitRepo(repoId);
            List<Issue> entityIssues = new ArrayList<>();
            value.forEach(entityIssue -> {
                Issue issue = new Issue(entityIssue.id());
                issue.setUrl(entityIssue.url());
                issue.setCreatedAt(entityIssue.createdAt());
                issue.setBody(entityIssue.body());
                issue.setClosedAt(entityIssue.closedAt());
                issue.setTitle(entityIssue.title());
                entityIssues.add(issue);
            });
            gitRepo.setIssues(entityIssues);
            gitRepository.save(gitRepo);
        });
    }

    public void storeGitRepo(File file) throws IOException {
        GitRepositoryData repositoryData = new ResponseJson().getRepositoryData(file);
        GitRepo gitRepo = new GitRepo(repositoryData.id());
        gitRepo.setUrl(repositoryData.url());
        gitRepo.setCreatedAt(repositoryData.createdAt());
        gitRepo.setUpdatedAt(repositoryData.updatedAt());
        gitRepo.setPrivateRepository(repositoryData.isPrivate());
        gitRepository.save(gitRepo);
    }
}

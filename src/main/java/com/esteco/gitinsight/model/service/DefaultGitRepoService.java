package com.esteco.gitinsight.model.service;

import com.esteco.gitinsight.model.entity.*;
import com.esteco.gitinsight.model.repository.GitRepository;
import com.esteco.gitinsight.model.repository.LabelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class DefaultGitRepoService implements GitRepoService {

    @Autowired
    private GitRepository gitRepository;
    @Autowired
    private LabelRepository labelRepository;

//    @Override
//    public void loadRepoData() {
//        GitRepo gitRepo = new GitRepo();
//        gitRepo.setRepoName("test-repo");
//        gitRepo.setUrl("https://github.com/test-repo");
//        gitRepo.setCreatedAt(LocalDateTime.from(LocalDateTime.now()));
//        gitRepo.setUpdatedAt(LocalDateTime.from(LocalDateTime.now()));
//        gitRepo.setRepoOwner("test-owner");
//        gitRepo.setForkedCount(666);
//        gitRepo.setIssuesCount(666);
//        gitRepo.setLabelCount(666);
//        gitRepo.setLanguageCount(666);
//        gitRepository.save(gitRepo);
//    }

    @Override
    public void loadRepoAndLabelsData() {
        Label label1 = new Label("testLabel1", "testColor1");
        Label label2 = new Label("testLabel2", "testColor2");
        Label label3 = new Label("testLabel3", "testColor3");
        Label label4 = new Label("testLabel4", "testColor4");
        GitRepo gitRepo = new GitRepo();
        Issue issue = new Issue();
        issue.setTitle("Sample Issue Title");
        issue.setTotalComment(5);
        issue.setUrl("https://github.com/example/repo/issues/1");
        issue.setBody("This is a sample issue body with detailed description.");
        issue.setClosedAt(LocalDateTime.now().minusDays(2));
        issue.setPrCount(2);
        issue.setClosed(true);
        issue.setCreatedAt(LocalDateTime.now().minusDays(10));
        issue.setLabelCount(3);
        List <Label> labels = new ArrayList<>();
        labels.add(label1);
        labels.add(label2);
        issue.setLabels(labels);


        Comment comment1 = new Comment();
        Comment comment2 = new Comment();
        comment1.setBody("111This is a sample comment body with detailed description.");
        comment2.setBody("222 This is a sample comment body with detailed description.");

        comment1.setCreatedAt(LocalDateTime.now().minusDays(2));
        comment2.setCreatedAt(LocalDateTime.now().minusDays(10));

        List <Comment> comments= new ArrayList<>();
        comments.add(comment1);
        comments.add(comment2);
        issue.setComments(comments);
        issue.setGitRepo(gitRepo);

        Author author1 = new Author();
        author1.setUrl("https://github.com/example/repo/authors/1");
        author1.setUsername("abcde");
        issue.setAuthor(author1);
        Author author2 = new Author();
        author2.setUrl("https://github.com/example/repo/authors/1");
        author2.setUsername("abcde");

        List <Author> authors= new ArrayList<>();
        authors.add(author1);
        authors.add(author2);

        issue.setAssignees(authors);
        PullRequest pullRequest = new PullRequest();
        pullRequest.setTitle("Add new feature");
        pullRequest.setBody("This pull request adds a new feature to the project.");
        pullRequest.setUrl("https://github.com/example/repo/pull/123");
        pullRequest.setFileChangeCount(15);
        pullRequest.setCommitCount(5);
        pullRequest.setCreatedAt(LocalDateTime.now().minusDays(7));
        pullRequest.setClosedAt(LocalDateTime.now().minusDays(2));
        pullRequest.setClosed(true);
        pullRequest.setAuthor(author1);

        List<Issue> issues = new ArrayList<>();
        issues.add(issue);
        pullRequest.setAssociatedIssues(issues);
        Commit commit = new Commit();
        commit.setUrl("https://github.com/example/repo/commits/1");
        commit.setPullRequest(pullRequest);
        commit.setAuthor(author1);
        List<Commit> commits = new ArrayList<>();
        commits.add(commit);
        pullRequest.setCommits(commits);

//        List<Issue> issues = new ArrayList<>();
//        issues.add(issue);
        gitRepo.setIssues(issues);
        gitRepo.setRepoName("test-repo");
        gitRepo.setUrl("https://github.com/test-repo");
        gitRepo.setCreatedAt(LocalDateTime.from(LocalDateTime.now()));
        gitRepo.setUpdatedAt(LocalDateTime.from(LocalDateTime.now()));
        gitRepo.setRepoOwner("test-owner");
        gitRepo.setForkedCount(666);
        gitRepo.setIssuesCount(666);
        gitRepo.setLabelCount(666);
        gitRepo.setLanguageCount(666);
        List<Label> labels1 = new ArrayList<>();
        labels1.add(label1);
        labels1.add(label2);
        labels1.add(label3);
        labels1.add(label4);

        gitRepo.setLabels(labels1);



        gitRepository.save(gitRepo);
//        List<Label> lab=   gitRepository.findAll().iterator().next().getLabels();

//        System.out.println(lab);
    }

}

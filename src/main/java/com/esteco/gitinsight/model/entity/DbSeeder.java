package com.esteco.gitinsight.model.entity;

import com.esteco.gitinsight.model.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class DbSeeder implements CommandLineRunner {
    private final GitRepository gitRepository;
    private final LabelRepository labelRepository;
    private final IssueRepository issueRepository;
    private final AuthorRepository authorRepository;
    private final CommentRepository commentRepository;

    public DbSeeder(GitRepository gitRepository, LabelRepository labelRepository, IssueRepository issueRepository, AuthorRepository authorRepository, CommentRepository commentRepository) {
        this.gitRepository = gitRepository;
        this.labelRepository = labelRepository;
        this.issueRepository = issueRepository;
        this.authorRepository = authorRepository;
        this.commentRepository = commentRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        GitRepo gitRepo = new GitRepo();
        gitRepo.setRepoName("test-repo");
        gitRepo.setUrl("https://github.com/test-repo");
        gitRepo.setRepoOwner("test-owner");
        gitRepo.setCreatedAt(LocalDateTime.from(LocalDateTime.now()));
        gitRepo.setUpdatedAt(LocalDateTime.from(LocalDateTime.now()));
        /*Label label = new Label("test-label", "red", gitRepo);
        Label label1 = new Label("test-label1", "pink", gitRepo);*/

        gitRepository.save(gitRepo);

        Author author = new Author("john");
        authorRepository.save(author);

        Issue issue = new Issue();
        Issue issue1 = new Issue();
        issue.setGitRepo(gitRepo);
        issue.setAuthor(author);
        issue1.setGitRepo(gitRepo);
        issue1.setAuthor(author);
        issueRepository.save(issue);
        issueRepository.save(issue1);

        Label label = new Label();
        label.setName("test-label");
        label.setColor("green");
        label.setIssue(issue);
        Label label1 = new Label();
        label1.setName("test-label1");
        label1.setColor("red");
        label1.setIssue(issue1);
        labelRepository.save(label);
        labelRepository.save(label1);

        Comment comment = new Comment();
        comment.setBody("This is a comment");
        comment.setIssue(issue);
        Comment comment1 = new Comment();
        comment1.setBody("This is a comment1");
        comment1.setIssue(issue1);
        commentRepository.save(comment);
        commentRepository.save(comment1);


        /*Language language = new Language("test-language", "red", gitRepo);
        gitRepo.setLanguages(List.of(language));
        Comment comment = new Comment();
        comment.setBody("This is a comment");
        Comment comment1 = new Comment();
        comment1.setBody("This is a comment1");
        Author author = new Author();
        author.setUsername("test-author");
        Author author1 = new Author();
        author1.setUsername("test-author1");
        PullRequest pullRequest = new PullRequest();
        pullRequest.setAuthor(author);
        Commit commit = new Commit();
        commit.setAuthor(author1);
        pullRequest.setCommits(List.of(commit));
        Issue issue = new Issue();
        issue.setTitle("test-title");
        issue.setTotalComment(10);
        issue.setUrl("test-url");
        issue.setBody("test-body");
        issue.setCreatedAt(LocalDateTime.now());
        issue.setClosedAt(LocalDateTime.now());
        issue.setClosed(false);
        issue.setPrCount(19);
        issue.setLabelCount(1);*/

        /*label.setIssue(issue);
        label1.setIssue(issue);
        gitRepo.setLabels(List.of(label, label1));*/
        /*comment.setIssue(issue);
        comment1.setIssue(issue);*/

        /*issue.setLabels(List.of(label, label1));
        issue.setComments(List.of(comment, comment1));
        issue.setGitRepo(gitRepo);
        issue.setAuthor(author);
        issue.setAssignees(List.of(author, author1));
        issue.setAssociatedPullRequests(List.of(pullRequest));*/


        //:TODO create comment
        //:TODO create label
        //:TODO create author
        //:TODO create pull request with empty commits
        //: TODO create issue. issue.setComment, issue.setLabel
        //: TODO gitRepo.setIssue
        //: TODO save gitRepo


//        gitRepository.save(gitRepo);
    }

    /*@Override
    public void run(String... args) throws Exception {
        Optional<GitRepo> gitData = gitRepository.findById("f1c21a4a-753d-48cd-916a-87b22b16d6aa");
        gitData.ifPresent(new Consumer<GitRepo>() {
            @Override
            public void accept(GitRepo g) {
                Hibernate.initialize(g.getLabels());
                List<Label> labels = g.getLabels();
                Label label = new Label("testLabel", "orange", gitData.get());
                labels.add(label);
                gitRepository.save(g);
//                System.out.println(g.getRepoName());
            }
        });
        System.out.println(gitData.toString());

        *//*Label label = new Label("testLabel", "orange", gitData.get());
//        gitData.get().setLabels(List.of(label));
        labelRepository.save(label);*//*
    }*/
}

package com.esteco.gitinsight.model.entity;

import com.esteco.gitinsight.model.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

//@Component
public class DbSeeder implements CommandLineRunner {
    private final GitRepository gitRepository;
    private final LabelRepository labelRepository;
    private final LanguageRepository languageRepository;
    private final IssueRepository issueRepository;

    public DbSeeder(GitRepository gitRepository, LabelRepository labelRepository, LanguageRepository languageRepository, IssueRepository issueRepository) {
        this.gitRepository = gitRepository;
        this.labelRepository = labelRepository;
        this.languageRepository = languageRepository;
        this.issueRepository = issueRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        gitRepository.deleteAll();

//        git repo
        GitRepo gitRepo = new GitRepo();
        gitRepo.setUrl("https://github.com/test-repo");
        gitRepo.setRepoOwner("test-owner");
        gitRepo.setRepoName("test-repo");
        gitRepo.setCreatedAt(LocalDateTime.from(LocalDateTime.now()));
        gitRepo.setUpdatedAt(LocalDateTime.from(LocalDateTime.now()));
        gitRepo.setPrivateRepository(false);



//        language1
        Language language = new Language();
        language.setName("name");
        language.setColor("blue");
        languageRepository.save(language);

//        language2
        Language language2 = new Language();
        language2.setName("language2");
        language2.setColor("red");
        languageRepository.save(language2);

        Issue issue = createIssue("title", "body", "url", "issue-1");
        issueRepository.save(issue);

        Issue issue1 = createIssue("title", "body", "url", "issue-2");
        issueRepository.save(issue1);

        gitRepo.setIssues(List.of(issue, issue1));
        gitRepo.setLanguages(Arrays.asList(language, language2));
        gitRepository.save(gitRepo);

//:TODO remove relationshiop one to many from GitRepo to issue. In issue table
//:TODO introduce git_repo_id
//        issueRepository.delete(issue1);
    }

    private Issue createIssue(String title, String body, String url, String id) {
        Issue issue = new Issue(id);
        issue.setTitle(title);
        issue.setBody(body);
        issue.setUrl(url);
        issue.setCreatedAt(LocalDateTime.from(LocalDateTime.now()));
        issue.setClosedAt(LocalDateTime.from(LocalDateTime.now()));

//        label1
        Label label = new Label();
        label.setName("label");
        label.setColor("blue");
        labelRepository.save(label);

//        label2
        Label label2 = new Label();
        label2.setName("label2");
        label2.setColor("red");
        labelRepository.save(label2);

//        label3
        Label label3 = new Label();
        label3.setName("label3");
        label3.setColor("green");
        labelRepository.save(label3);

        issue.setLabels(Arrays.asList(label, label2,label3));

        Commit commit = new Commit();
        commit.setUrl("https://github.com/test-repo");


        PullRequest pullRequest = new PullRequest();
        pullRequest.setBody("body");
        pullRequest.setCreatedAt(LocalDateTime.from(LocalDateTime.now()));
        pullRequest.setClosedAt(LocalDateTime.from(LocalDateTime.now()));
        pullRequest.setUrl("test-url");
        pullRequest.setCommits(List.of(commit));

        issue.setPullRequest(pullRequest);

        GitUser creator = new GitUser();
        creator.setUsername("test-creator");
        GitUser assignee = new GitUser();
        assignee.setUsername("test-assignee");

        Comment comment = new Comment();
        comment.setBody("body");
        comment.setCreatedAt(LocalDateTime.from(LocalDateTime.now()));
        issue.setComments(List.of(comment));
        issue.setAuthor(creator);
        issue.setAssignees(List.of(assignee,creator));
        return issue;
    }
}

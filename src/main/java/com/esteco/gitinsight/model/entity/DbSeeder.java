package com.esteco.gitinsight.model.entity;

import com.esteco.gitinsight.model.repository.GitRepository;
import com.esteco.gitinsight.model.repository.LabelRepository;
import org.hibernate.Hibernate;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

@Component
public class DbSeeder implements CommandLineRunner {
    private final GitRepository gitRepository;
    private final LabelRepository labelRepository;

    public DbSeeder(GitRepository gitRepository, LabelRepository labelRepository) {
        this.gitRepository = gitRepository;
        this.labelRepository = labelRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        GitRepo gitRepo = new GitRepo();
        gitRepo.setRepoName("test-repo");
        gitRepo.setUrl("https://github.com/test-repo");
        gitRepo.setRepoOwner("test-owner");
        gitRepo.setCreatedAt(LocalDateTime.from(LocalDateTime.now()));
        gitRepo.setUpdatedAt(LocalDateTime.from(LocalDateTime.now()));
        Label label = new Label("test-label", "red", gitRepo);
        Label label1 = new Label("test-label1", "pink", gitRepo);
//        gitRepo.setLabels(List.of(label,label1));

        Language language = new Language("test-language", "red", gitRepo);
        gitRepo.setLanguages(List.of(language));
        Comment comment = new Comment();
        comment.setBody("This is a comment");
        comment.setIssue();
        Comment comment1 = new Comment();
        comment1.setBody("This is a comment1");
        Author author = new Author();
        author.setUsername("test-author");
        Issue issue = new Issue("test-title", 10, "test-url", "test-body", LocalDateTime.now(), 19, false, LocalDateTime.now(),
                1, List.of(label, label1), List.of(comment, comment1), gitRepo, author, null, null);
        gitRepo.setIssues(List.of(issue));

        //:TODO create comment
        //:TODO create label
        //:TODO create author
        //:TODO create pull request with empty commits
        //: TODO create issue. issue.setComment, issue.setLabel
        //: TODO gitRepo.setIssue
        //: TODO save gitRepo


        gitRepository.save(gitRepo);
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

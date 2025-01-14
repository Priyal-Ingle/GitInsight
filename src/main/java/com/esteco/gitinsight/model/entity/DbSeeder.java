package com.esteco.gitinsight.model.entity;

import com.esteco.gitinsight.model.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;

@Component
public class DbSeeder implements CommandLineRunner {
    private final GitRepository gitRepository;
    private final LabelRepository labelRepository;
    private final LanguageRepository languageRepository;

    public DbSeeder(GitRepository gitRepository, LabelRepository labelRepository, LanguageRepository languageRepository) {
        this.gitRepository = gitRepository;
        this.labelRepository = labelRepository;
        this.languageRepository = languageRepository;
    }

    @Override
    public void run(String... args) throws Exception {

//        git repo
        GitRepo gitRepo = new GitRepo();
        gitRepo.setUrl("https://github.com/test-repo");
        gitRepo.setRepoOwner("test-owner");
        gitRepo.setRepoName("test-repo");
        gitRepo.setCreatedAt(LocalDateTime.from(LocalDateTime.now()));
        gitRepo.setUpdatedAt(LocalDateTime.from(LocalDateTime.now()));
        gitRepo.setPrivateRepository(false);

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

//        language1
        Language language = new Language();
        language.setName("language");
        language.setColor("blue");
        languageRepository.save(language);

//        language2
        Language language2 = new Language();
        language2.setName("language2");
        language2.setColor("red");
        languageRepository.save(language2);

//        Issue
        Issue issue = new Issue();
        issue.setTitle("title");
        issue.setBody("body");
        issue.setUrl("url");
        issue.setCreatedAt(LocalDateTime.from(LocalDateTime.now()));
        issue.setClosedAt(LocalDateTime.from(LocalDateTime.now()));


        gitRepo.setLabels(Arrays.asList(label, label2));
        gitRepo.setLanguages(Arrays.asList(language, language2));
        gitRepository.save(gitRepo);
    }
}

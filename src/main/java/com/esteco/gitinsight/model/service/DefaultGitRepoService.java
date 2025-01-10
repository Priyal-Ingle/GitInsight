package com.esteco.gitinsight.model.service;

import com.esteco.gitinsight.model.entity.GitRepo;
import com.esteco.gitinsight.model.entity.Label;
import com.esteco.gitinsight.model.repository.GitRepository;
import com.esteco.gitinsight.model.repository.LabelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
        GitRepo gitRepo = new GitRepo();
        Label label1 = new Label("testLabel1", "testColor1");
        Label label2 = new Label("testLabel2", "testColor2");


        gitRepo.setRepoName("test-repo");
        gitRepo.setUrl("https://github.com/test-repo");
        gitRepo.setCreatedAt(LocalDateTime.from(LocalDateTime.now()));
        gitRepo.setUpdatedAt(LocalDateTime.from(LocalDateTime.now()));
        gitRepo.setRepoOwner("test-owner");
        gitRepo.setForkedCount(666);
        gitRepo.setIssuesCount(666);
        gitRepo.setLabelCount(666);
        gitRepo.setLanguageCount(666);
        gitRepo.setLabel(label1);
        gitRepo.setLabel(label2);
        gitRepository.save(gitRepo);
        List<Label> lab=   gitRepository.findAll().iterator().next().getLabels();

        System.out.println(lab);
    }

}

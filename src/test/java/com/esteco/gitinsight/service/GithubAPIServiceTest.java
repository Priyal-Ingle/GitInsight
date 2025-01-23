package com.esteco.gitinsight.service;

import com.esteco.gitinsight.model.repository.*;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

@SpringBootTest
@Transactional
@Rollback(false)
class GithubAPIServiceTest {

    @Autowired
    private GitRepository gitRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PullRequestRepository pullRequestRepository;

    @Autowired
    private LanguageRepository languageRepository;

    @Autowired
    private IssueRepository issueRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private LabelRepository labelRepository;

    private GithubAPIService githubAPIService;

    @BeforeEach
    void setUp() throws Exception {
        this.githubAPIService = new GithubAPIService(gitRepository, languageRepository, commentRepository, issueRepository, pullRequestRepository, authorRepository, labelRepository);
    }

    @Test
    void testGetRepoDataService() throws Exception {
        String owner = "carbon-language";
        String repoName = "carbon-lang";
        githubAPIService.getRepoData(owner, repoName);
    }

}
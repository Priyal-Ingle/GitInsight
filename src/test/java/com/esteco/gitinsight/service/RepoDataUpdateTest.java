package com.esteco.gitinsight.service;

import com.esteco.gitinsight.model.repository.*;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.net.URL;

@SpringBootTest
@Transactional
@Rollback(false)
class RepoDataUpdateTest {

    @Autowired
    private GithubAPIService githubAPIService;


    @Test
    void testRepoUpdateService() throws Exception {
        String repoId = "R_kgDONucNCw";
        githubAPIService.updateRepoData(repoId);
    }



}
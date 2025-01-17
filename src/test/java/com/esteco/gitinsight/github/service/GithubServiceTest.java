package com.esteco.gitinsight.github.service;

import com.esteco.gitinsight.github.dto.Repository;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class GithubServiceTest {

    @Test
    void testGetRepoDataService() throws Exception {
        String expected = "Repository[id=MDEwO`lJlcG9zaXRvcnkyNTk0NjM2ODU=, url=null, updatedAt=null, createdAt=null, forkCount=0, isPrivate=false, isLocked=false, labels=null, languages=null, issues=null]";
        String owner = "quarkusio";
        String repoName = "quarkus";
        GithubService githubService = new GithubService();

        Repository response = githubService.getRepoData(owner, repoName);

        assertNotNull(response);
        String actual = response.toString();
        assertThat(actual, containsString("id=MDEwOlJlcG9zaXRvcnkxMzk5MTQ5MzI="));
        assertThat(actual, containsString("createdAt=2018-07-06T00:44:20Z"));
    }
}
package com.esteco.gitinsight.github.service;

import com.esteco.gitinsight.github.client.GithubClient;
import com.esteco.gitinsight.github.dto.Repository;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;

public class GithubService {

    public Repository getRepoData(String owner, String repoName) throws IOException, URISyntaxException {

        URL queryUrl = GithubService.class.getResource("../../query.resources");
        File query = new File(queryUrl.toURI());
        String queryString = Files.readString(query.toPath());
        queryString = queryString.replace("@owner-name", owner);
        queryString = queryString.replace("@repo-name", repoName);

        String url = TestConfig.GITHUB_URL;
        String token = TestConfig.GITHUB_TOKEN;
        GithubClient githubClient = new GithubClient(url, token);


        Repository repoDetail = githubClient.getRepoDetail(queryString).block();
        return repoDetail;
    }
}

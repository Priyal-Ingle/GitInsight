package com.esteco.gitinsight.controller;

import com.esteco.gitinsight.service.GithubAPIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URISyntaxException;

@RestController
public class RepositoryController {

    @Autowired
    private GithubAPIService githubAPIService;

    @GetMapping("/api/repository/fetch")
    public String fetchAndStoreRepoData(@RequestParam(name = "owner") String owner, @RequestParam(name = "repoName") String repoName) throws IOException, URISyntaxException {
        githubAPIService.getRepoData(owner, repoName);
        return "Data for repository '" + repoName + "' by owner '" + owner + "' has been fetched and stored successfully.";
    }
}

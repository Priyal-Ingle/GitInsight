package com.esteco.gitinsight.github.client;

import com.esteco.gitinsight.config.TestConfig;
import com.esteco.gitinsight.github.dto.Repository;
import org.junit.jupiter.api.Test;
import org.springframework.graphql.client.HttpSyncGraphQlClient;


import java.net.MalformedURLException;

import static org.junit.jupiter.api.Assertions.*;

public class GithubClientTest {

    @Test
    void testCreateRestClientWhenUsingInvalidUrl() {
        String url = "htt://api.github.com/graphql";
        GithubClient githubClient = new GithubClient(url, "token");

        MalformedURLException exception = assertThrows(MalformedURLException.class, githubClient::createGraphQlClient);

        assertEquals("Invalid URL: " + url, exception.getMessage());
    }

    @Test
    void testCreateGraphQlClient() throws Exception {
        String url = TestConfig.GITHUB_URL;
        String token = TestConfig.GITHUB_TOKEN;
        GithubClient githubClient = new GithubClient(url, token);

        HttpSyncGraphQlClient graphQlClient = githubClient.createGraphQlClient();

        assertNotNull(graphQlClient);
    }

    @Test
    void testGithubQueryResponse() throws Exception {
        String expected = TestConfig.EXPECTED_TEST_RESPONSE;
        String url = TestConfig.GITHUB_URL;
        String token = TestConfig.GITHUB_TOKEN;
        String query = TestConfig.GITHUB_TEST_QUERY;
        GithubClient githubClient = new GithubClient(url, token);

        Repository response= githubClient.getRepoDetail(query).block();

        assertNotNull(response);
        assertEquals(expected,response.toString());

    }

}

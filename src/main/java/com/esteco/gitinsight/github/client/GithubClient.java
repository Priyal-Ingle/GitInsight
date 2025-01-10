package com.esteco.gitinsight.github.client;

import com.esteco.gitinsight.github.dto.Repository;
import org.springframework.graphql.client.HttpSyncGraphQlClient;
import org.springframework.web.client.RestClient;
import reactor.core.publisher.Mono;

import java.net.MalformedURLException;
import java.net.URI;

public class GithubClient {

    private final String url;
    private final String token;

    public GithubClient(String url, String token) {
        this.url = url;
        this.token = token;
    }

    private RestClient createRestClient(String url) throws MalformedURLException {
        if (url.startsWith("https")) {
            URI baseUrl = URI.create(url);
            return RestClient.create(baseUrl);
        }
        throw new MalformedURLException("Invalid URL: " + url);
    }

    private HttpSyncGraphQlClient createGraphQlClient(RestClient restClient, String token) {
        return HttpSyncGraphQlClient.builder(restClient)
                .header("Authorization", "bearer " + token)
                .build();
    }

    HttpSyncGraphQlClient createGraphQlClient() throws MalformedURLException {
        return createGraphQlClient(createRestClient(url), token);
    }

    public Mono<Repository> getRepoDetail(String query) throws MalformedURLException {
        HttpSyncGraphQlClient graphQlClient= createGraphQlClient();
        return graphQlClient.document(query)
                .retrieve("repository")
                .toEntity(Repository.class);
    }
}

package com.esteco.gitinsight.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@ConfigurationPropertiesScan
@ConfigurationProperties(prefix = "github")
public class ConfigProperties {

    private String graphqlUrl;
    private String token;

    public String getGraphqlUrl() {
        return graphqlUrl;
    }

    public void setGraphqlUrl(String graphqlUrl) {
        this.graphqlUrl = graphqlUrl;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}

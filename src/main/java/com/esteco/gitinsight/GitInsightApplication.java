package com.esteco.gitinsight;

import com.esteco.gitinsight.config.ConfigProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@EnableConfigurationProperties(ConfigProperties.class)
public class GitInsightApplication {

    public static void main(String[] args) {
       SpringApplication.run(GitInsightApplication.class, args);
    }

}

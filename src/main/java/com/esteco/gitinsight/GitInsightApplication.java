package com.esteco.gitinsight;

import com.esteco.gitinsight.model.service.DefaultGitRepoService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class GitInsightApplication {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(GitInsightApplication.class, args);

        DefaultGitRepoService defaultGitRepoService = context.getBean(DefaultGitRepoService.class);

//        defaultGitRepoService.loadRepoData();
    }

}

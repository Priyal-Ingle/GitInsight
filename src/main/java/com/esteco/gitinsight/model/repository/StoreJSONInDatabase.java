package com.esteco.gitinsight.model.repository;

import com.esteco.gitinsight.config.ConfigProperties;
import component.PersistResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.io.File;
import java.io.IOException;

@Component
public class StoreJSONInDatabase {


    private final PersistResponse persistResponse;

    @Autowired
    public StoreJSONInDatabase(GitRepository gitRepository,
                               LanguageRepository languageRepository,
                               CommentRepository commentRepository,
                               IssueRepository issueRepository,
                               PullRequestRepository pullRequestRepository, AuthorRepository authorRepository, LabelRepository labelRepository, ConfigProperties configProperties) {
        this.persistResponse = new PersistResponse(gitRepository, languageRepository, commentRepository, issueRepository, pullRequestRepository, authorRepository,labelRepository, configProperties);
    }

    public void persistFileInDB(File file) throws IOException {
        persistResponse.storeLanguages(file);
        persistResponse.storePullRequestCommits(file);
        persistResponse.storeIssueAuthor(file);
        persistResponse.storeComments(file);
        persistResponse.storeCommentAuthor(file);
        persistResponse.storeIssueAssignees(file);
        persistResponse.storeIssueLabels(file);
        persistResponse.storePullRequests(file);
        persistResponse.storeIssues(file);
        persistResponse.storeGitRepo(file);
    }
}

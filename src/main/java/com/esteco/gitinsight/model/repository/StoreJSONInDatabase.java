package com.esteco.gitinsight.model.repository;

import com.esteco.gitinsight.model.entity.Issue;
import com.esteco.gitinsight.response.PersistResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

@Service
public class StoreJSONInDatabase {


    private final PersistResponse persistResponse;
    private final AuthorRepository authorRepository;
    private final LabelRepository labelRepository;

    @Autowired
    public StoreJSONInDatabase(IssueRepository issueRepository1, GitRepository gitRepository,
                               LanguageRepository languageRepository,
                               CommentRepository commentRepository,
                               IssueRepository issueRepository,
                               PullRequestRepository pullRequestRepository, AuthorRepository authorRepository, LabelRepository labelRepository) {
        this.labelRepository = labelRepository;
        this.persistResponse = new PersistResponse(gitRepository, languageRepository, commentRepository, issueRepository, pullRequestRepository, authorRepository,labelRepository);
        this.authorRepository = authorRepository;
    }

    public void persistFileInDB(File file) throws IOException {
        persistResponse.storeLanguages(file);
        persistResponse.storePullRequestCommits(file);
        persistResponse.storeCommentAuthor(file);
        persistResponse.storeComments(file);
        persistResponse.storeIssueAssignees(file);
        persistResponse.storeIssueLabels(file);
        persistResponse.storePullRequests(file);
//        persistResponse.storeIssues(file);
//        persistResponse.storeIssueAuthor(file);
//        persistResponse.storeGitRepo(file);
    }
}

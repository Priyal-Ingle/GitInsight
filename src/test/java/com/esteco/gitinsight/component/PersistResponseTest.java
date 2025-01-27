package com.esteco.gitinsight.component;

import com.esteco.gitinsight.config.ConfigProperties;
import com.esteco.gitinsight.model.repository.*;
import component.PersistResponse;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@Transactional
@Rollback(false)
public class PersistResponseTest {
    private PersistResponse persistResponse;

    @Mock
    private GitRepository gitRepository;

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private PullRequestRepository pullRequestRepository;

    @Mock
    private LanguageRepository languageRepository;

    @Mock
    private LabelRepository labelRepository;

    @Mock
    private AuthorRepository authorRepository;

    @Mock
    private IssueRepository issueRepository;

    @Mock
    private ConfigProperties configProperties;
    private File file;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        persistResponse = new PersistResponse(gitRepository, languageRepository, commentRepository, issueRepository, pullRequestRepository, authorRepository, labelRepository, configProperties);
        URL queryUrl = PersistResponseTest.class.getResource("../GraphQLResponse.json");
        file = new File(queryUrl.toURI());
    }

    @Test
    void testSaveLanguage() throws URISyntaxException, IOException {
        persistResponse.storeLanguages(file);

        verify(languageRepository, times(4)).save(any());
        verify(gitRepository).save(any());
        verify(commentRepository, never()).save(any());
        verify(issueRepository, never()).save(any());
    }

    @Test
    void testSaveComment() throws URISyntaxException, IOException {
        persistResponse.storeComments(file);

        verify(commentRepository,never()).save(any());
        verify(issueRepository, never()).save(any());
        verify(languageRepository, never()).save(any());
        verify(gitRepository, never()).save(any());
    }

    @Test
    void testSaveIssueAuthor() throws IOException {
        persistResponse.storeIssueAuthor(file);

        verify(issueRepository, times(12)).save(any());
        verify(commentRepository, never()).save(any());
        verify(languageRepository, never()).save(any());
        verify(gitRepository, never()).save(any());
    }

    @Test
    void testSaveCommentAuthor() throws IOException {
        persistResponse.storeCommentAuthor(file);

        verify(issueRepository, never()).save(any());
        verify(commentRepository, times(15)).save(any());
        verify(languageRepository, never()).save(any());
        verify(gitRepository, never()).save(any());
    }

    @Test
    void testSavePullRequestCommits() throws IOException {
        persistResponse.storePullRequestCommits(file);

        verify(pullRequestRepository, times(2)).save(any());
        verify(issueRepository, never()).save(any());
        verify(commentRepository, never()).save(any());
        verify(languageRepository, never()).save(any());
        verify(gitRepository, never()).save(any());
    }

    @Test
    void testSavePullRequests() throws IOException {
        persistResponse.storePullRequests(file);

        verify(pullRequestRepository, never()).save(any());
        verify(issueRepository, never()).save(any());
        verify(commentRepository, never()).save(any());
        verify(languageRepository, never()).save(any());
        verify(gitRepository, never()).save(any());
    }

    @Test
    void testSaveIssueAssignees() throws IOException {
        persistResponse.storeIssueAssignees(file);

        verify(pullRequestRepository, never()).save(any());
        verify(issueRepository, never()).save(any());
        verify(commentRepository, never()).save(any());
        verify(languageRepository, never()).save(any());
        verify(gitRepository, never()).save(any());
    }

    @Test
    void testSaveIssueLabels() throws IOException {
        persistResponse.storeIssueLabels(file);

        verify(pullRequestRepository, never()).save(any());
        verify(issueRepository, never()).save(any());
        verify(commentRepository, never()).save(any());
        verify(languageRepository, never()).save(any());
        verify(gitRepository, never()).save(any());
    }

    @Test
    void testSaveIssues() throws IOException {
        persistResponse.storeIssues(file);

        verify(pullRequestRepository, never()).save(any());
        verify(issueRepository, never()).save(any());
        verify(commentRepository, never()).save(any());
        verify(languageRepository, never()).save(any());
        verify(gitRepository, times(1)).save(any());
    }

    @Test
    void testSaveGitRepo() throws IOException {
        persistResponse.storeGitRepo(file);

        verify(pullRequestRepository, never()).save(any());
        verify(issueRepository, never()).save(any());
        verify(commentRepository, never()).save(any());
        verify(languageRepository, never()).save(any());
        verify(gitRepository, times(1)).save(any());

    }
}

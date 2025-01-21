package com.esteco.gitinsight.model.repository;

import com.esteco.gitinsight.response.PersistResponse;
import com.esteco.gitinsight.response.PersistResponseTest;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@SpringBootTest
//@Transactional
//@Rollback(false)
public class StoreJSONToDatabaseTest {
    @Mock
    private GitRepository gitRepository;

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private PullRequestRepository pullRequestRepository;

    @Mock
    private LanguageRepository languageRepository;

    @Mock
    private IssueRepository issueRepository;

    @Mock
    private AuthorRepository authorRepository;

    @Mock
    private LabelRepository labelRepository;

    private StoreJSONInDatabase storeJSONInDatabase;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        storeJSONInDatabase = new StoreJSONInDatabase(gitRepository, languageRepository, commentRepository, issueRepository, pullRequestRepository, authorRepository, labelRepository);

    }

    @Test
    void testStoreFromJSONInDatabase() throws URISyntaxException, IOException {
        URL queryUrl = RelationshipInDatabaseTest.class.getResource("../../response/GraphQLResponse.json");
        assert queryUrl != null;
        File file = new File(queryUrl.toURI());

        storeJSONInDatabase.persistFileInDB(file);

        verify(pullRequestRepository, times(6)).save(any());
        verify(issueRepository, times(10)).save(any());
        verify(commentRepository, times(8)).save(any());
        verify(languageRepository, times(2)).save(any());
        verify(gitRepository, times(3)).save(any());



    }
}

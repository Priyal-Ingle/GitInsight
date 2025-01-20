package com.esteco.gitinsight.model.repository;

import com.esteco.gitinsight.response.PersistResponse;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
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
    @Autowired
    private GitRepository gitRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PullRequestRepository pullRequestRepository;

    @Autowired
    private LanguageRepository languageRepository;

    @Autowired
    private IssueRepository issueRepository;

    @Autowired
    private StoreJSONInDatabase storeJSONInDatabase;

    @Test
    void testStoreFromJSONInDatabase() throws URISyntaxException, IOException {
        URL queryUrl = RelationshipInDatabaseTest.class.getResource("../../response/GraphQLResponse.json");
        assert queryUrl != null;
        File file = new File(queryUrl.toURI());

        storeJSONInDatabase.persistFileInDB(file);

//        verify(pullRequestRepository, times(1)).save(any());
//        verify(issueRepository, times(1)).save(any());
//        verify(commentRepository, times(1)).save(any());
//        verify(languageRepository, times(1)).save(any());
//        verify(gitRepository, times(1)).save(any());



    }
}

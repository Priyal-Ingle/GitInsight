package com.esteco.gitinsight.model.repository;

import com.esteco.gitinsight.config.ConfigProperties;
import com.esteco.gitinsight.model.entity.GitRepo;
import com.esteco.gitinsight.model.entity.Language;
import component.PersistResponse;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
//@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.NONE)
@Transactional
@Rollback(false)
public class RelationshipInDatabaseTest {
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
    private AuthorRepository authorRepository;

    @Autowired
    private LabelRepository labelRepository;

    @Autowired
    private ConfigProperties configProperties;
    @Test
    void testGitLanguageRelationDatabase() throws Exception {
        PersistResponse persistResponse = new PersistResponse(gitRepository, languageRepository, commentRepository,
                issueRepository, pullRequestRepository, authorRepository, labelRepository, configProperties );
        URL queryUrl = RelationshipInDatabaseTest.class.getResource("../../GraphQLResponse.json");
        File file = new File(queryUrl.toURI());


        persistResponse.storeLanguages(file);

        Optional<Language> dbLanguage1 = languageRepository.findById("MDg6TGFuZ3VhZ2UxNDU=");
        Language language1 = dbLanguage1.get();
        assertEquals("#3572A5", language1.getColor());
        assertEquals("Python", language1.getName());
        Optional<GitRepo> dbGitRepository = gitRepository.findById("R_kgDONXxB9w");

        GitRepo gitRepo = dbGitRepository.get();
        List<Language> languages = gitRepo.getLanguages();
        assertFalse(languages.isEmpty());
        assertEquals("Python", languages.get(0).getName());
    }
}

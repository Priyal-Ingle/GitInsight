package com.esteco.gitinsight.model.repository;

import com.esteco.gitinsight.model.entity.GitRepo;
import com.esteco.gitinsight.model.entity.Language;
import com.esteco.gitinsight.response.PersistResponse;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.io.File;
import java.net.URISyntaxException;
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

    @Test
    void testGitLanguageRelationDatabase() throws Exception {
        PersistResponse persistResponse = new PersistResponse(gitRepository, languageRepository, commentRepository,
                issueRepository, pullRequestRepository, authorRepository, labelRepository );
        URL queryUrl = RelationshipInDatabaseTest.class.getResource("../../response/GraphQLResponse.json");
        File file = new File(queryUrl.toURI());


        persistResponse.storeLanguages(file);

        Optional<Language> dbLanguage1 = languageRepository.findById("MDg6TGFuZ3VhZ2UxNTg=");
        Language language1 = dbLanguage1.get();
        assertEquals("#b07219", language1.getColor());
        assertEquals("Java", language1.getName());
        Optional<Language> dbLanguage2 = languageRepository.findById("MDjedhfgTGFuZ3VhZ2UxNTg=");
        Language language2 = dbLanguage2.get();
        assertEquals("#b08919", language2.getColor());
        assertEquals("C++", language2.getName());
        Optional<GitRepo> dbGitRepository = gitRepository.findById("MDEwOlJlcG9zaXRvcnkxMzk5MTQ5MzI=");

        GitRepo gitRepo = dbGitRepository.get();
        List<Language> languages = gitRepo.getLanguages();
        assertFalse(languages.isEmpty());
        assertEquals("Java", languages.get(0).getName());

    }


}

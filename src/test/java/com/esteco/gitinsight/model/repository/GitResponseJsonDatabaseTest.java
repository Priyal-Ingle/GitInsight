package com.esteco.gitinsight.model.repository;

import com.esteco.gitinsight.HibernateUtil;
import com.esteco.gitinsight.model.entity.GitRepo;
import com.esteco.gitinsight.model.entity.Language;
import com.esteco.gitinsight.response.PersistResponse;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.lang.module.Configuration;
import java.net.URL;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
//@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class GitResponseJsonDatabaseTest {
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

    @Test
    void testStoreInDatabase() throws Exception {
        PersistResponse persistResponse = new PersistResponse(gitRepository, languageRepository, commentRepository,
                issueRepository, pullRequestRepository);
        URL queryUrl = GitResponseJsonDatabaseTest.class.getResource("../../response/GraphQLResponse.json");
        File file = new File(queryUrl.toURI());

//        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
//        Session currentSession = sessionFactory.getCurrentSession();

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
        Hibernate.initialize(gitRepo.getLanguages());
        List<Language> languages = gitRepo.getLanguages();
        assertFalse(languages.isEmpty());
        assertEquals("Java", languages.get(0).getName());


    }


}

package com.esteco.gitinsight.model.repository;

import com.esteco.gitinsight.model.entity.Language;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class LanguageRepositoryTest {

    @Autowired
    private LanguageRepository languageRepository;

    @Test
    void testInsertLanguageInDatabase() {
        Language entityLanguage = new Language("English");
        entityLanguage.setName("ddfd");
        entityLanguage.setColor("dfdf");

        languageRepository.save(entityLanguage);

        Optional<Language> dbLanguage = languageRepository.findById("English");
        if (dbLanguage.isPresent()) {
            Language language = dbLanguage.get();
            assertEquals("dfdf",language.getColor());
            assertEquals("ddfd",language.getName());
        }
    }

    @Test
    void testDeleteLanguageInDatabase() {
        Language entityLanguage = new Language("id_1");
        entityLanguage.setName("Java");
        entityLanguage.setColor("Black");
        languageRepository.save(entityLanguage);

        languageRepository.deleteById("id_1");

        Optional<Language> dbLanguage = languageRepository.findById("id_1");
        assertFalse(dbLanguage.isPresent());
    }
}

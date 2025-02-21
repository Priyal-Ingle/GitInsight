package com.esteco.gitinsight.model.repository;

import com.esteco.gitinsight.model.entity.Language;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LanguageRepository extends CrudRepository<Language, String> {
}

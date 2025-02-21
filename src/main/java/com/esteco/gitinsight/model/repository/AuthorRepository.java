package com.esteco.gitinsight.model.repository;

import com.esteco.gitinsight.model.entity.GitUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthorRepository extends CrudRepository<GitUser, String> {
    Optional<GitUser> findByUsername(String username);
}

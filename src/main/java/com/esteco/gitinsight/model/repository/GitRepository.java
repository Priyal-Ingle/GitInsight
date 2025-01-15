package com.esteco.gitinsight.model.repository;

import com.esteco.gitinsight.model.entity.GitRepo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GitRepository extends CrudRepository<GitRepo, String> {

}

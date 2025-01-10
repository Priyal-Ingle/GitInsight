package com.esteco.gitinsight.model.repository;

import com.esteco.gitinsight.model.entity.Issue;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IssueRepository extends CrudRepository<Issue, String> {



}

package com.esteco.gitinsight.model.repository;

import com.esteco.gitinsight.model.entity.Comment;
import com.esteco.gitinsight.model.entity.PullRequest;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PullRequestRepository extends CrudRepository<PullRequest, String> {

}

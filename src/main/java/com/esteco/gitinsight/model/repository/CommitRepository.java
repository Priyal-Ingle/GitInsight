package com.esteco.gitinsight.model.repository;

import com.esteco.gitinsight.model.entity.Commit;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommitRepository extends CrudRepository<Commit, String> {

}

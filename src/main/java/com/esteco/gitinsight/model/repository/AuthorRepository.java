package com.esteco.gitinsight.model.repository;

import com.esteco.gitinsight.model.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends CrudRepository<User, String> {

}

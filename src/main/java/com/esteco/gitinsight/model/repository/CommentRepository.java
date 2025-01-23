package com.esteco.gitinsight.model.repository;

import com.esteco.gitinsight.model.entity.Comment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends CrudRepository<Comment, String> {

}

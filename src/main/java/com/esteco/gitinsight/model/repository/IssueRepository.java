package com.esteco.gitinsight.model.repository;

import com.esteco.gitinsight.model.entity.Issue;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IssueRepository extends CrudRepository<Issue, String>, JpaSpecificationExecutor<Issue>, PagingAndSortingRepository<Issue, String> {

    List<Issue> findByAuthor_Id(String authorId);

    @Query("SELECT i FROM Issue i JOIN i.labels l WHERE l.id = :labelId")
    List<Issue> findIssueByLabelId(@Param("labelId") String labelId, Pageable pageable);
}

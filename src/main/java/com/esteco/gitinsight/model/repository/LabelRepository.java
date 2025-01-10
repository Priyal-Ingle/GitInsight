package com.esteco.gitinsight.model.repository;


import com.esteco.gitinsight.model.entity.Label;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LabelRepository extends CrudRepository<Label, String> {

}

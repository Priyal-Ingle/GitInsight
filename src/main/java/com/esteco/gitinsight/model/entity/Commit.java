package com.esteco.gitinsight.model.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "COMMIT")
public class Commit {
    @Id
    private String id;
    private String url;

    @OneToMany(
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    private List<FileChanges> fileChanges = new ArrayList<>();

//    *********************getter setter start ********************

    public List<FileChanges> getFileChanges() {
        return fileChanges;
    }

    public void setFileChanges(List<FileChanges> fileChanges) {
        this.fileChanges.addAll(fileChanges);
    }
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Commit(String id) {
        this.id=id;
    }

    public Commit(){
    }

}

package com.esteco.gitinsight.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.UUID;

@Entity
public class FileChanges {
    @Id
    private String id;

    private String filePath;

    private String status;

    public FileChanges() {
        this(UUID.randomUUID().toString());
    }

    public FileChanges(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}

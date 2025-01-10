package com.esteco.gitinsight.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import static java.util.UUID.randomUUID;

@Entity

public class Language {
    @Id

    private String id;
    private String name;
    private String color;


    public Language() {
        this(randomUUID().toString());
    }

    public Language(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}

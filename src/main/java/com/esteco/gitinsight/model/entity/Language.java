package com.esteco.gitinsight.model.entity;

import jakarta.persistence.*;

import java.util.UUID;

import static java.util.UUID.randomUUID;

@Entity
@Table(name = "LANGUAGE", uniqueConstraints = {
        @UniqueConstraint(columnNames = "name")
})
public class Language {
    @Id
    private String id;
    private String name;
    private String color;

//    *********************getter setter start ********************
    public Language() {
        this(randomUUID().toString());
    }

    public Language(String name, String color) {
        this(UUID.randomUUID().toString());
        this.name = name;
        this.color = color;
    }

    public void setId(String id) {
        this.id = id;
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

package com.esteco.gitinsight.model.entity;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
public class Label {

    @Id
    private String id;
    private String name;
    private String color;

//**************************** getter and setter for issue**************************************

    public Label(String id) {
        this.id = id;
    }

    public Label(String name, String color) {
        this(UUID.randomUUID().toString());
        this.name = name;
        this.color = color;
    }

    public Label() {
        this(UUID.randomUUID().toString());
    }

    public void setId(String id) {
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

    @Override
    public String toString() {
        return "Label [id=" + id + ", name=" + name + ", color=" + color + "]";
    }

}

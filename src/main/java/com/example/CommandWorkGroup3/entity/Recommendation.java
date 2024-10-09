package com.example.CommandWorkGroup3.entity;



import java.util.UUID;



public class Recommendation {

    public Recommendation(UUID id, String name, String description, String condition) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.condition = condition;
    }

    private UUID id;


    private String name;


    private String description;

    private String condition;

    public String getCondition() {
        return condition;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}

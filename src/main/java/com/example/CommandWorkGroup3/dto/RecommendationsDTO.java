package com.example.CommandWorkGroup3.dto;

public class RecommendationsDTO {

    public String id;
    public String text;
    public String name;

    public RecommendationsDTO(String id, String name, String text) {
        this.id = id;
        this.name = name;
        this.text = text;
    }

    public RecommendationsDTO() {

    }

    public void Recommendations() {}


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Recommendations{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}

package com.example.CommandWorkGroup3.dto;

//@Entity
public class RecommendationsDTO {


    //    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public String id;
    public String text;
    public String name;
//    private final UUID user;

    public RecommendationsDTO(String id, String name, String text) {
        this.id = id;
        this.name = name;
        this.text = text;
//        this.user = user;
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

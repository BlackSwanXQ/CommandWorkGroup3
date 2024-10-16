package com.example.CommandWorkGroup3.entity;

//@Entity
public class Recommendations {


    //    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public String id;
    public String text;
    public String name;
//    private final UUID user;

    public Recommendations(String id, String name, String text) {
        this.id = id;
        this.name = name;
        this.text = text;
//        this.user = user;
    }
//    public Recommendations() {}


    @Override
    public String toString() {
        return "Recomendations{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}

package com.example.CommandWorkGroup3.recomendations;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.boot.autoconfigure.domain.EntityScan;


public class Recomendations {

    public String name;
    public String id;
    public String text;
//    private final UUID user;

    public Recomendations(String name, String id, String text) {
        this.name = name;
        this.id = id;
        this.text = text;
//        this.user = user;
    }


    @Override
    public String toString() {
        return "Recomendations{" +
                "name='" + name + '\'' +
                ", id='" + id + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}

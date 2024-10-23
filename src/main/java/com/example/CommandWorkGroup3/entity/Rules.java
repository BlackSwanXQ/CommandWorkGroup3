package com.example.CommandWorkGroup3.entity;

import jakarta.persistence.*;
import netscape.javascript.JSObject;

import java.util.*;

@Entity
public class Rules {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String query;
    private String arguments;
    private Boolean negate;


    public Rules(Long id, String query, String arguments, Boolean negate) {
        this.id = id;
        this.query = query;
        this.arguments = arguments;
        this.negate = negate;
    }



    public Rules() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getArguments() {
        return arguments;
    }

    public void setArguments(String arguments) {
        this.arguments = arguments;
    }

    public Boolean getNegate() {
        return negate;
    }

    public void setNegate(Boolean negate) {
        this.negate = negate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rules rules = (Rules) o;
        return Objects.equals(id, rules.id) && Objects.equals(query, rules.query) && Objects.equals(arguments, rules.arguments) && Objects.equals(negate, rules.negate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, query, arguments, negate);
    }

    @Override
    public String toString() {
        return "Rules{" +
                "id=" + id +
                ", query='" + query + '\'' +
                ", arguments='" + arguments + '\'' +
                ", negate=" + negate +
                '}';
    }
}

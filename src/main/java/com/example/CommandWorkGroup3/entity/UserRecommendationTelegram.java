package com.example.CommandWorkGroup3.entity;

import jakarta.persistence.*;

import java.util.List;
import java.util.Set;

@Entity
@Table(name = "recommendationstelegram")
public class UserRecommendationTelegram {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private Long userId;
    private int count;


    @ManyToOne
    @JoinColumn(name="rules_id")
    private Rules rules;

    public UserRecommendationTelegram(long id, long userId, int count
    ) {
        this.id = id;
        this.userId = userId;
        this.count = 0;
    }

    public UserRecommendationTelegram() {
    }


    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public Long getUserId(Long id) {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Rules getRule() {
        return rules;
    }

    public void setRule(Rules rules) {
        this.rules = rules;
    }

    @Override
    public String toString() {
        return "UserRecommendationTelegram{" +
                "id=" + id +
                ", userId=" + userId +
                ", count=" + count +
                ", rules=" + rules +
                '}';
    }
}

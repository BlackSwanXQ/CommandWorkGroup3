package com.example.CommandWorkGroup3.entity;

import com.example.CommandWorkGroup3.rules.AbstractRule;
import com.example.CommandWorkGroup3.converter.RulesConverter;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "recommendations_v2")
@Data
public class RecommendationV2 {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "condition", nullable = false)
    @Convert(converter = RulesConverter.class)
    private Set<AbstractRule> condition;


}

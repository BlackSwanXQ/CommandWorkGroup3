package com.example.CommandWorkGroup3.interfaces;

import com.example.CommandWorkGroup3.recomendations.Recomendations;
import org.springframework.http.ResponseEntity;

import java.util.Optional;
import java.util.UUID;

public interface RecommendationRuleSet {

Recomendations getRecommendation(UUID user);

}

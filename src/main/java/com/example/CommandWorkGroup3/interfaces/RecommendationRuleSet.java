package com.example.CommandWorkGroup3.interfaces;

import com.example.CommandWorkGroup3.dto.RecommendationsDTO;

import java.util.Optional;
import java.util.UUID;

public interface RecommendationRuleSet {

Optional<RecommendationsDTO> getRecommendation(UUID user);

}

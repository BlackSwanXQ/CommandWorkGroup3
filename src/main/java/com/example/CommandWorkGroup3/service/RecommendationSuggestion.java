package com.example.CommandWorkGroup3.service;

import com.example.CommandWorkGroup3.dto.RecommendationDto;

import java.util.Collection;
import java.util.UUID;

public interface RecommendationSuggestion {

    Collection<RecommendationDto> getRecommendationsForClient(UUID clientId);
}

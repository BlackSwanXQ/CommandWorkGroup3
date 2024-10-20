package com.example.CommandWorkGroup3.service;

import com.example.CommandWorkGroup3.dto.RecommendationDto;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface RecommendationManagement {

    Page<RecommendationDto> getRecommendations(int page, int size);

    RecommendationDto getRecommendationById(UUID id);

    RecommendationDto createRecommendation(RecommendationDto dto);

    RecommendationDto updateRecommendation(UUID id, RecommendationDto dto);

    void deleteRecommendation(UUID id);
}

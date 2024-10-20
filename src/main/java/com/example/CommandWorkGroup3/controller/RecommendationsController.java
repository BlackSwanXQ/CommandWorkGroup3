package com.example.CommandWorkGroup3.controller;


import com.example.CommandWorkGroup3.dto.RecommendationDto;
import com.example.CommandWorkGroup3.dto.ResultDto;
import com.example.CommandWorkGroup3.service.RecommendationSuggestion;
import com.example.CommandWorkGroup3.service.RecommendationsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;


@RestController
@RequestMapping("/recommendations")
@RequiredArgsConstructor
public class RecommendationsController {

    private final Set<RecommendationSuggestion> services;

    @GetMapping("/{clientId}")
    public ResultDto getRecommendations(@PathVariable UUID clientId) {
        Set<RecommendationDto> recommendations = new HashSet<>();
        services.forEach(s -> recommendations.addAll(s.getRecommendationsForClient(clientId)));
        return new ResultDto(clientId, recommendations);
    }


}

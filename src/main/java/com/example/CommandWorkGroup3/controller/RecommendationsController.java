package com.example.CommandWorkGroup3.controller;


import com.example.CommandWorkGroup3.dto.ResultDto;
import com.example.CommandWorkGroup3.service.RecommendationsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;


@RestController
@RequestMapping("/recommendations")
public class RecommendationsController {

    private final RecommendationsService recommendationsService;

    public RecommendationsController(RecommendationsService recommendationsService) {
        this.recommendationsService = recommendationsService;
    }

    @GetMapping("/{clientId}")
    public ResultDto getRecommendations(@PathVariable UUID clientId) {
        return recommendationsService.getRecommendations(clientId);
    }


}

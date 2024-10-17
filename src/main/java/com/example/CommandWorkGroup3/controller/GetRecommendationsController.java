package com.example.CommandWorkGroup3.controller;

import com.example.CommandWorkGroup3.services.RecommendationsService;
import com.example.CommandWorkGroup3.dto.RecommendationsDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/recommendations")
@Tag(name = "Recommendations API", description = "API for management recommendations")
public class GetRecommendationsController {

    private final RecommendationsService recommendationsService;

    public GetRecommendationsController(RecommendationsService recommendationsService) {
        this.recommendationsService = recommendationsService;
    }


    @GetMapping()
    @Operation(summary = "Return recommendations", description = "Returns recommendations")
    @ApiResponse(responseCode = "200", description = "Successful response")
    public List<Optional<RecommendationsDTO>> getRecommendations(UUID user) {
        return recommendationsService.getRecommendation(user);
    }
}

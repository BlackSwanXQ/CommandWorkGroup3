package com.example.CommandWorkGroup3.controller;

import com.example.CommandWorkGroup3.DTO.RecommendationsDTO;
import com.example.CommandWorkGroup3.entity.Recommendations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/recommendations")
public class GetRecommendationsController {

    private final RecommendationsDTO recommendationsDTO;

    public GetRecommendationsController(RecommendationsDTO recommendationsDTO) {
        this.recommendationsDTO = recommendationsDTO;
    }


    @GetMapping()
    public List<Recommendations> getRecommendationsInvest500(UUID user) {
        return recommendationsDTO.getRecommendation(user);
    }
}

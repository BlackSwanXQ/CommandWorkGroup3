package com.example.CommandWorkGroup3.controller;

import com.example.CommandWorkGroup3.DTO.RecommendationsDTO;
import com.example.CommandWorkGroup3.recomendations.Recomendations;
import com.example.CommandWorkGroup3.services.Invest500;
import com.example.CommandWorkGroup3.services.SimpleCredit;
import com.example.CommandWorkGroup3.services.TopSaving;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collections;
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
    public List<Recomendations> getRecommendationsInvest500(UUID user) {
        return recommendationsDTO.getRecommendation(user);
    }
}

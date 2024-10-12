package com.example.CommandWorkGroup3.DTO;

import com.example.CommandWorkGroup3.interfaces.RecommendationRuleSet;
import com.example.CommandWorkGroup3.recomendations.Recomendations;
import com.example.CommandWorkGroup3.services.Invest500;
import com.example.CommandWorkGroup3.services.SimpleCredit;
import com.example.CommandWorkGroup3.services.TopSaving;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class RecommendationsDTO {

    private final Invest500 invest500;
    private final SimpleCredit simpleCredit;
    private final TopSaving topSaving;

    RecommendationsDTO(Invest500 invest500,
                       SimpleCredit simpleCredit,
                       TopSaving topSaving) {

        this.invest500 = invest500;
        this.simpleCredit = simpleCredit;
        this.topSaving = topSaving;
    }

    public List<Recomendations> getRecommendation(UUID user) {
        List<Recomendations> recommendations = Arrays.asList(
                invest500.getRecommendation(user),
                simpleCredit.getRecommendation(user),
                topSaving.getRecommendation(user));


        return recommendations.stream()
                .filter(recomm -> recomm != null)
                .collect(Collectors.toList());
    }

}

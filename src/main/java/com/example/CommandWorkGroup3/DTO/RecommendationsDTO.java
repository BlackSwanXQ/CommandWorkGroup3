package com.example.CommandWorkGroup3.DTO;

import com.example.CommandWorkGroup3.entity.Recommendations;
import com.example.CommandWorkGroup3.services.Invest500;
import com.example.CommandWorkGroup3.services.SimpleCredit;
import com.example.CommandWorkGroup3.services.SimpleCreditFromDB;
import com.example.CommandWorkGroup3.services.TopSaving;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class RecommendationsDTO {

    private final Invest500 invest500;
    private final SimpleCredit simpleCredit;
    private final TopSaving topSaving;
    private final SimpleCreditFromDB simpleCreditFromDB;

    RecommendationsDTO(Invest500 invest500,
                       SimpleCredit simpleCredit,
                       TopSaving topSaving,
                       SimpleCreditFromDB simpleCreditFromDB
    ) {

        this.invest500 = invest500;
        this.simpleCredit = simpleCredit;
        this.topSaving = topSaving;
        this.simpleCreditFromDB = simpleCreditFromDB;
    }

    public List<Recommendations> getRecommendation(UUID user) {
        List<Recommendations> recommendations = Arrays.asList(
                invest500.getRecommendation(user),
                simpleCredit.getRecommendation(user),
                topSaving.getRecommendation(user),
                simpleCreditFromDB.getRecommendation(user)
        );


        return recommendations.stream()
                .filter(recomm -> recomm != null)
                .collect(Collectors.toList());
    }

}

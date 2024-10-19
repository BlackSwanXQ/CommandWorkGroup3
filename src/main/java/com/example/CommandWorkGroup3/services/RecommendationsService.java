package com.example.CommandWorkGroup3.services;

import com.example.CommandWorkGroup3.dto.RecommendationsDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class RecommendationsService {

    private final Invest500 invest500;
    private final SimpleCredit simpleCredit;
    private final TopSaving topSaving;
    private final SimpleCreditFromDB simpleCreditFromDB;

    private final Logger logger = LoggerFactory.getLogger(RulesService.class);

    RecommendationsService(Invest500 invest500,
                           SimpleCredit simpleCredit,
                           TopSaving topSaving,
                           SimpleCreditFromDB simpleCreditFromDB
    ) {

        this.invest500 = invest500;
        this.simpleCredit = simpleCredit;
        this.topSaving = topSaving;
        this.simpleCreditFromDB = simpleCreditFromDB;
    }

    public List<Optional<RecommendationsDTO>> getRecommendation(UUID user) {
        logger.info("Was invoked method for get recommendations with user id: " + user.toString());
        List<Optional<RecommendationsDTO>> recommendations = Arrays.asList(
                invest500.getRecommendation(user),
                simpleCredit.getRecommendation(user),
                topSaving.getRecommendation(user),
                simpleCreditFromDB.getRecommendation(user)
        );
        return recommendations.stream()
                .filter(recomm -> recomm.isPresent())
                .collect(Collectors.toList());
    }

}

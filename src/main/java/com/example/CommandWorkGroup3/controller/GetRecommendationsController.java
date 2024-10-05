package com.example.CommandWorkGroup3.controller;

import com.example.CommandWorkGroup3.services.Invest500;
import com.example.CommandWorkGroup3.services.SimpleCredit;
import com.example.CommandWorkGroup3.services.TopSaving;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/recommendations")
public class GetRecommendationsController {

    private final Invest500 invest500;
    private final SimpleCredit simpleCredit;
    private final TopSaving topSaving;

    public GetRecommendationsController(@Qualifier("invest500") Invest500 invest500,
                                        @Qualifier("simpleCredit") SimpleCredit simpleCredit,
                                        @Qualifier("topSaving") TopSaving topSaving) {
        this.invest500 = invest500;
        this.simpleCredit = simpleCredit;
        this.topSaving = topSaving;
    }


    @GetMapping("/invest500")
    public String getRecommendationsInvest500(UUID user) {
        return invest500.getRecommendation(user);
    }

    @GetMapping("/simpleCredit")
    public String getRecommendationsSimpleCredit(UUID user) {
        return simpleCredit.getRecommendation(user);
    }

    @GetMapping("/topSaving")
    public String getRecommendationsTopSaving(UUID user) {
        return topSaving.getRecommendation(user);
    }


}

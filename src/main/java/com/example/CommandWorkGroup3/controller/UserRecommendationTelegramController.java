package com.example.CommandWorkGroup3.controller;

import com.example.CommandWorkGroup3.entity.Rules;
import com.example.CommandWorkGroup3.entity.UserRecommendationTelegram;
import com.example.CommandWorkGroup3.services.RulesService;
import com.example.CommandWorkGroup3.services.UserRecommendationsTelegramService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/recommendations")
@Tag(name = "Rules API", description = "API for management userrecommendations")
public class UserRecommendationTelegramController {

    private final UserRecommendationsTelegramService userRecommendationsTelegramService;

    public UserRecommendationTelegramController(UserRecommendationsTelegramService userRecommendationsTelegramService) {
        this.userRecommendationsTelegramService = userRecommendationsTelegramService;
    }

    @PostMapping
    @Operation(summary = "Add a userrecommendation", description = "Adds a userrecomendation")
    @ApiResponse(responseCode = "200", description = "Successful response")
    public UserRecommendationTelegram addRule(@RequestBody UserRecommendationTelegram userRecommendationTelegram) {
        return userRecommendationsTelegramService.addRuleRecommendation(userRecommendationTelegram);
    }

    @GetMapping("/stats")
    @Operation(summary = "Get statistics for count", description = "Get statisrics for count ")
    @ApiResponse(responseCode = "200", description = "Successful response")
    public List<UserRecommendationTelegram> getRuleStatistics() {
        return userRecommendationsTelegramService.getStat();
    }
}

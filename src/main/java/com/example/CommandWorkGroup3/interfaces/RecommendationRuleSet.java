package com.example.CommandWorkGroup3.interfaces;

import com.example.CommandWorkGroup3.entity.Recommendations;

import java.util.UUID;

public interface RecommendationRuleSet {

Recommendations getRecommendation(UUID user);

}

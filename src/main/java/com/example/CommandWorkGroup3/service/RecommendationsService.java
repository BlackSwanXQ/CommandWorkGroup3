package com.example.CommandWorkGroup3.service;

import com.example.CommandWorkGroup3.dao.RecommendationDao;
import com.example.CommandWorkGroup3.dao.TransactionDao;
import com.example.CommandWorkGroup3.dto.ResultDto;
import com.example.CommandWorkGroup3.entity.Recommendation;
import com.example.CommandWorkGroup3.mapper.RecommendationMapper;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
public class RecommendationsService {

    private final TransactionDao transactionDao;
     private final RecommendationDao recommendationDao;

    private final RecommendationMapper recommendationMapper;

    public RecommendationsService(TransactionDao transactionDao, RecommendationDao recommendationDao, RecommendationMapper recommendationMapper) {
        this.transactionDao = transactionDao;
        this.recommendationDao = recommendationDao;
        this.recommendationMapper = recommendationMapper;
    }

    public ResultDto getRecommendations(UUID clientId) {

        Set<Recommendation> recommendations = recommendationDao.getRecommendations().stream()
                .filter(r -> transactionDao.checkCondition(clientId, r.getCondition()))
                .collect(Collectors.toSet());

        return new ResultDto(clientId, recommendations.stream().map(recommendationMapper::toDto).collect(Collectors.toSet()));

    }




}

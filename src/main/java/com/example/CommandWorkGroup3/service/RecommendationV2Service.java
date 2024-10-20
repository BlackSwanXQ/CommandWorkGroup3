package com.example.CommandWorkGroup3.service;

import com.example.CommandWorkGroup3.dao.TransactionDao;
import com.example.CommandWorkGroup3.dto.RecommendationDto;
import com.example.CommandWorkGroup3.entity.Recommendation;
import com.example.CommandWorkGroup3.entity.RecommendationV2;
import com.example.CommandWorkGroup3.exception.NotFoundException;
import com.example.CommandWorkGroup3.mapper.RecommendationMapper;
import com.example.CommandWorkGroup3.repository.RecommendationV2Repository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecommendationV2Service implements RecommendationManagement, RecommendationSuggestion {

    private final RecommendationV2Repository repository;

    private final RecommendationMapper mapper;

    private final TransactionDao transactionDao;


    private RecommendationV2 getById(UUID id) {
        return repository.findById(id).orElseThrow(()-> new NotFoundException(String.format("Рекомендация ID %s не найдена", id)));
    }

    @Override
    public Page<RecommendationDto> getRecommendations(int page, int size) {
        return repository.findAll(PageRequest.of(page, size)).map(mapper::toDtoWithCondition);
    }

    @Override
    public RecommendationDto getRecommendationById(UUID id) {
        return mapper.toDtoWithCondition(getById(id));
    }

    @Override
    public RecommendationDto createRecommendation(RecommendationDto dto) {
        RecommendationV2 recommendation = repository.save(mapper.fromDto(dto));
        return mapper.toDtoWithCondition(recommendation);
    }

    @Override
    @Transactional
    public RecommendationDto updateRecommendation(UUID id, RecommendationDto dto) {
        RecommendationV2 recommendation = getById(id);
        mapper.fromDto(recommendation, dto);
        return mapper.toDtoWithCondition(recommendation);
    }

    @Override
    public void deleteRecommendation(UUID id) {
        RecommendationV2 recommendation = getById(id);
        repository.delete(recommendation);

    }

    @Override
    public Collection<RecommendationDto> getRecommendationsForClient(UUID clientId) {
        Set<RecommendationV2> recommendations = repository
                .findAll()
                .stream().filter(
                        recommendation -> recommendation.getCondition().stream().allMatch(
                                rule -> rule.apply(clientId, transactionDao)
                        )
                ).collect(Collectors.toSet());
        return recommendations.stream().map(mapper::toDto).collect(Collectors.toSet());
    }

}

package com.example.CommandWorkGroup3.mapper;

import com.example.CommandWorkGroup3.dto.RecommendationDto;
import com.example.CommandWorkGroup3.entity.Recommendation;
import com.example.CommandWorkGroup3.entity.RecommendationV2;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface RecommendationMapper {

    @Mapping(target = "condition", ignore = true)
    RecommendationDto toDto(Recommendation recommendation);

    @Mapping(target = "condition", ignore = true)
    RecommendationDto toDto(RecommendationV2 recommendationV2);

    RecommendationDto toDtoWithCondition(RecommendationV2 recommendationV2);

    @Mapping(target = "id", ignore = true)
    RecommendationV2 fromDto(RecommendationDto dto);

    @Mapping(target = "id", ignore = true)
    void fromDto(@MappingTarget RecommendationV2 target, RecommendationDto dto);
}

package com.example.CommandWorkGroup3.mapper;

import com.example.CommandWorkGroup3.dto.RecommendationDto;
import com.example.CommandWorkGroup3.entity.Recommendation;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface RecommendationMapper {

    RecommendationDto toDto(Recommendation recommendation);
}

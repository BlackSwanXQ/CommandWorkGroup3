package com.example.CommandWorkGroup3.dto;

import java.util.Set;
import java.util.UUID;

public record ResultDto(UUID clientId, Set<RecommendationDto> recommendations) {
}

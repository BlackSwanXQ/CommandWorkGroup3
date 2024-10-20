package com.example.CommandWorkGroup3.dto;

import com.example.CommandWorkGroup3.rules.AbstractRule;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.validation.annotation.Validated;

import java.util.Set;
import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record RecommendationDto(
        UUID id,
        @NotBlank String name,
        @NotBlank String description,
        @NotEmpty Set<@Valid ? extends AbstractRule> condition
) { }

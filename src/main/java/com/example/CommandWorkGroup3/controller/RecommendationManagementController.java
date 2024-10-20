package com.example.CommandWorkGroup3.controller;

import com.example.CommandWorkGroup3.dto.RecommendationDto;
import com.example.CommandWorkGroup3.service.RecommendationManagement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/management/recommendations")
@RequiredArgsConstructor
public class RecommendationManagementController {

    private final RecommendationManagement service;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<RecommendationDto> getRecommendations(@RequestParam(required = false, defaultValue = "0") int page,
                                                      @RequestParam(required = false, defaultValue = "10") int size) {
        return service.getRecommendations(page, size);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public RecommendationDto getRecommendationById(@PathVariable UUID id) {
        return service.getRecommendationById(id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public RecommendationDto createRecommendation(@RequestBody @Valid RecommendationDto dto) {
        return service.createRecommendation(dto);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public RecommendationDto upDateRecommendation(@PathVariable UUID id, @RequestBody @Valid RecommendationDto dto) {
        return service.updateRecommendation(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deleteRecommendation(@PathVariable UUID id) {
        service.deleteRecommendation(id);
    }

}

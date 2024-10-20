package com.example.CommandWorkGroup3.repository;

import com.example.CommandWorkGroup3.entity.RecommendationV2;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RecommendationV2Repository extends JpaRepository<RecommendationV2, UUID> {


}

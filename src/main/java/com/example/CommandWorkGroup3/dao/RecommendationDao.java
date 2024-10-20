package com.example.CommandWorkGroup3.dao;

import com.example.CommandWorkGroup3.entity.Recommendation;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class RecommendationDao {

    private final JdbcTemplate recommendationJdbcTemplate;

    public RecommendationDao(JdbcTemplate recommendationJdbcTemplate) {
        this.recommendationJdbcTemplate = recommendationJdbcTemplate;
    }

    public List<Recommendation> getRecommendations() {
        return recommendationJdbcTemplate.query(
                "SELECT * FROM recommendations;",
                ((rs, rowNum) -> new Recommendation(
                        UUID.fromString(rs.getString("id")),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getString("condition")))
        );
    }
}

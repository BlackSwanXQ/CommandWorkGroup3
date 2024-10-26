package com.example.CommandWorkGroup3.repository;

import com.example.CommandWorkGroup3.entity.NotificationTask;
import com.example.CommandWorkGroup3.entity.UserRecommendationTelegram;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface UserRecommendationTelegramRepository extends JpaRepository<UserRecommendationTelegram, Long> {
    public List<UserRecommendationTelegram> findAllByUserId(long telegramId);
    public UserRecommendationTelegram findByRulesId(Long id);
    public UserRecommendationTelegram findByRulesId(long userId);
}

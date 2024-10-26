package com.example.CommandWorkGroup3.services;

import com.example.CommandWorkGroup3.entity.Rules;
import com.example.CommandWorkGroup3.entity.UserRecommendationTelegram;
import com.example.CommandWorkGroup3.repository.UserRecommendationTelegramRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserRecommendationsTelegramService {
    private final UserRecommendationTelegramRepository userRecommendationTelegramRepository;
    private final Logger logger = LoggerFactory.getLogger(UserRecommendationsTelegramService.class);

    UserRecommendationsTelegramService(UserRecommendationTelegramRepository userRecommendationTelegramRepository) {
        this.userRecommendationTelegramRepository = userRecommendationTelegramRepository;
    }

    public UserRecommendationTelegram addRuleRecommendation(UserRecommendationTelegram userRecommendationTelegram) {
        logger.info("Was invoked method for add rule recommendation");
        return userRecommendationTelegramRepository.save(userRecommendationTelegram);
    }

    public List<UserRecommendationTelegram> getStat() {
        logger.info("Was invoked method for get statistics");
        return userRecommendationTelegramRepository.findAll();
    }
}

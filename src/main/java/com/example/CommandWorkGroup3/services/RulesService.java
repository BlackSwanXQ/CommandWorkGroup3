package com.example.CommandWorkGroup3.services;

import com.example.CommandWorkGroup3.entity.Rules;
import com.example.CommandWorkGroup3.entity.UserRecommendationTelegram;
import com.example.CommandWorkGroup3.exceptions.RuleNotFoundException;
import com.example.CommandWorkGroup3.interfaces.RulesRepository;
import com.example.CommandWorkGroup3.repository.UserRecommendationTelegramRepository;
import jakarta.persistence.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@Service
public class RulesService {
    private final RulesRepository rulesRepository;
    private final Logger logger = LoggerFactory.getLogger(RulesService.class);
    private final UserRecommendationTelegramRepository telegramRepository;
    private final UserRecommendationTelegramRepository userRecommendationTelegramRepository;

    @PersistenceUnit
    private EntityManagerFactory entityManagerFactory;


    RulesService(RulesRepository rulesRepository,
                 UserRecommendationTelegramRepository telegramRepository,
                 UserRecommendationTelegramRepository userRecommendationTelegramRepository) {
        this.rulesRepository = rulesRepository;
        this.telegramRepository = telegramRepository;
        this.userRecommendationTelegramRepository = userRecommendationTelegramRepository;
    }

    public Rules addRule(Rules rule) {
        logger.info("Was invoked method for create rule with query: " + rule.getQuery());
        return rulesRepository.save(rule);
    }

    public Rules deleteRule(Long id) {
        logger.info("Was invoked method for delete rule with id: " + id);
        Rules rule = rulesRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("There is not Student with id = " + id);
                    return new RuleNotFoundException(id);
                });

        Long ruleId = userRecommendationTelegramRepository.findByRulesId(id).getId();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        UserRecommendationTelegram ruleTelegram =
                entityManager.find(UserRecommendationTelegram.class, ruleId);
        ruleTelegram.setRule(null);
        ruleTelegram.setCount(0);

        telegramRepository.save(ruleTelegram);

        rulesRepository.delete(rule);
        logger.debug("Rule with id {} was removed", rule.getId());
        return rule;
    }

    public List<Rules> getAllRules() {
        logger.info("Was invoked method for get all rules");
        return rulesRepository.findAll();
    }


}

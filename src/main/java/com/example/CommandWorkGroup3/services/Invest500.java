package com.example.CommandWorkGroup3.services;

import com.example.CommandWorkGroup3.interfaces.RecommendationRuleSet;
import com.example.CommandWorkGroup3.recomendations.Recomendations;
import com.example.CommandWorkGroup3.repository.RecommendationsRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.UUID;

@Component("invest500")
public class Invest500 implements RecommendationRuleSet {
    private final RecommendationsRepository recommendationsRepository;
    private final String name = "invest500";
    private final String id = "147f6a0f-3b91-413b-ab99-87f081d60d5a";
    private final String text = "Откройте свой путь к успеху " +
            "с индивидуальным инвестиционным счетом (ИИС) " +
            "от нашего банка! " +
            "Воспользуйтесь налоговыми льготами и начните инвестировать с умом. " +
            "Пополните счет до конца года и получите выгоду в виде вычета " +
            "на взнос в следующем налоговом периоде. " +
            "Не упустите возможность разнообразить свой портфель, " +
            "снизить риски и следить за актуальными рыночными тенденциями. " +
            "Откройте ИИС сегодня и станьте ближе к финансовой независимости!";

    Invest500(RecommendationsRepository recommendationsRepository) {
        this.recommendationsRepository = recommendationsRepository;
    }


    @Override
    public Recomendations getRecommendation(UUID user) {
        Recomendations recomendations = new Recomendations(name, id, text);
        if (recommendationsRepository.getInvest500(user)) {
            return recomendations;
        } else return null;

    }
}

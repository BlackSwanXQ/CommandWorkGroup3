package com.example.CommandWorkGroup3.services;

import com.example.CommandWorkGroup3.interfaces.RecommendationRuleSet;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component("invest500")
public class Invest500 implements RecommendationRuleSet {
    private final String name = "invest500";
    private final String id = "147f6a0f-3b91-413b-ab99-87f081d60d5a";
    private final String description = "Откройте свой путь к успеху " +
            "с индивидуальным инвестиционным счетом (ИИС) " +
            "от нашего банка! " +
            "Воспользуйтесь налоговыми льготами и начните инвестировать с умом. " +
            "Пополните счет до конца года и получите выгоду в виде вычета " +
            "на взнос в следующем налоговом периоде. " +
            "Не упустите возможность разнообразить свой портфель, " +
            "снизить риски и следить за актуальными рыночными тенденциями. " +
            "Откройте ИИС сегодня и станьте ближе к финансовой независимости!";

    @Override
    public String getRecommendation(UUID user) {
        return "invest 500";
    }

}

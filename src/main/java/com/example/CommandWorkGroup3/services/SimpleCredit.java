package com.example.CommandWorkGroup3.services;

import com.example.CommandWorkGroup3.interfaces.RecommendationRuleSet;
import com.example.CommandWorkGroup3.recomendations.Recomendations;
import com.example.CommandWorkGroup3.repository.RecommendationsRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component("simpleCredit")
public class SimpleCredit implements RecommendationRuleSet {
    private final RecommendationsRepository recommendationsRepository;
    private final String name = "Простой кредит";
    private final String id = "ab138afb-f3ba-4a93-b74f-0fcee86d447f";
    private final String text = "Откройте мир выгодных кредитов с нами! " +
            "Ищете способ быстро и без лишних хлопот получить нужную сумму? Тогда наш выгодный кредит — именно то, что вам нужно! Мы предлагаем низкие процентные ставки, гибкие условия и индивидуальный подход к каждому клиенту. " +
            "Почему выбирают нас: " +
            "Быстрое рассмотрение заявки. Мы ценим ваше время, поэтому процесс рассмотрения заявки занимает всего несколько часов. " +
            "Удобное оформление. Подать заявку на кредит можно онлайн на нашем сайте или в мобильном приложении. " +
            "Широкий выбор кредитных продуктов. Мы предлагаем кредиты на различные цели: покупку недвижимости, автомобиля, образование, лечение и многое другое. " +
            "Не упустите возможность воспользоваться выгодными условиями кредитования от нашей компании!";

    SimpleCredit(RecommendationsRepository recommendationsRepository) {
        this.recommendationsRepository = recommendationsRepository;
    }

    @Override
    public Recomendations getRecommendation(UUID user) {
        Recomendations recomendations = new Recomendations(name, id, text);
        if(recommendationsRepository.getSimpleCredit(user)){
            return recomendations;
        } else return null;

    }
}

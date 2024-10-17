package com.example.CommandWorkGroup3.controller;

import com.example.CommandWorkGroup3.dto.RecommendationsDTO;
import com.example.CommandWorkGroup3.entity.Rules;
import com.example.CommandWorkGroup3.repository.RecommendationsRepository;
import com.example.CommandWorkGroup3.services.Invest500;
import com.example.CommandWorkGroup3.services.SimpleCredit;
import com.example.CommandWorkGroup3.services.SimpleCreditFromDB;
import com.example.CommandWorkGroup3.services.TopSaving;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class GetRecommendationsControllerRestTemplateTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private RecommendationsRepository recRepository;
    @Autowired
    private Invest500 invest500;
    @Autowired
    private SimpleCredit simpleCredit;
    @Autowired
    private SimpleCreditFromDB simpleCreditFromDB;
    @Autowired
    private TopSaving topSaving;


    @Test
    void getRecommendations() {
        RecommendationsDTO recommndations1 = new RecommendationsDTO("147f6a0f-3b91-413b-ab99-87f081d60d5a",
                "invest500",
                "Откройте свой путь к успеху с индивидуальным инвестиционным счетом (ИИС) от нашего банка! Воспользуйтесь налоговыми льготами и начните инвестировать с умом. Пополните счет до конца года и получите выгоду в виде вычета на взнос в следующем налоговом периоде. Не упустите возможность разнообразить свой портфель, снизить риски и следить за актуальными рыночными тенденциями. Откройте ИИС сегодня и станьте ближе к финансовой независимости!");

        RecommendationsDTO recommendations2 = new RecommendationsDTO("59efc529-2fff-41af-baff-90ccd7402925",
                "Top Saving",
                "Откройте свою собственную «Копилку» с нашим банком! «Копилка» — это уникальный банковский инструмент, который поможет вам легко и удобно накапливать деньги на важные цели. Больше никаких забытых чеков и потерянных квитанций — всё под контролем! Преимущества «Копилки»: Накопление средств на конкретные цели. Установите лимит и срок накопления, и банк будет автоматически переводить определенную сумму на ваш счет. Прозрачность и контроль. Отслеживайте свои доходы и расходы, контролируйте процесс накопления и корректируйте стратегию при необходимости. Безопасность и надежность. Ваши средства находятся под защитой банка, а доступ к ним возможен только через мобильное приложение или интернет-банкинг. Начните использовать «Копилку» уже сегодня и станьте ближе к своим финансовым целям!");
        UUID userId = UUID.fromString("cd515076-5d8a-44be-930e-8d4fcb79f42d");
        ResponseEntity<List<RecommendationsDTO>> forEntity = restTemplate.exchange(
                "/recommendations?user={userId}",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                },
                userId);
        Assertions.assertThat(forEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(forEntity.getBody()).isNotEmpty();
        Assertions.assertThat(forEntity.getBody()).hasSize(2);
        Assertions.assertThat(invest500.getRecommendation(userId).get().toString()).isEqualTo(recommndations1.toString());
        Assertions.assertThat(topSaving.getRecommendation(userId).get().toString()).isEqualTo(recommendations2.toString());
    }
}
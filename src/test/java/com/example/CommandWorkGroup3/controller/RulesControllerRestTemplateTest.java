package com.example.CommandWorkGroup3.controller;

import com.example.CommandWorkGroup3.entity.Rules;
import com.example.CommandWorkGroup3.repository.RulesRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;
import java.util.Random;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RulesControllerRestTemplateTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    RulesRepository rulesRepository;

    @BeforeEach
    void beforeEach() {
        Rules rule1 = new Rules();
        rule1.setQuery("USER_OF");
        rule1.setArguments("CREDIT");
        rule1.setNegate(true);
        rulesRepository.save(rule1);
        Rules rule2 = new Rules();
        rule2.setQuery("ACTIVE_USER_OF");
        rule2.setArguments("DEBIT");
        rule2.setNegate(false);
        rulesRepository.save(rule2);
        Rules rule3 = new Rules();
        rule3.setQuery("TRANSACTION_SUM_COMPARE_DEPOSIT_WITHDRAW");
        rule3.setArguments("DEBIT, >");
        rule3.setNegate(false);
        rulesRepository.save(rule3);
    }

    @AfterEach
    void afterEach() {
        rulesRepository.deleteAll();
    }

    @Test
    void addRule() {
        Rules rules = new Rules();
        rules.setQuery("TRANSACTION_SUM_COMPARE");
        rules.setArguments("DEBIT, DEPOSIT, >, 100000");
        rules.setNegate(false);

        ResponseEntity<Rules> responseEntity = restTemplate.postForEntity("/rules",
                rules,
                Rules.class
        );
        Rules created = responseEntity.getBody();

        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(created).isNotNull();
        Assertions.assertThat(created).usingRecursiveComparison()
                .ignoringFields("id").isEqualTo(rules);
        Assertions.assertThat(created.getId()).isNotNull();

        Optional<Rules> fromDB = rulesRepository.findById(created.getId());
        Assertions.assertThat(fromDB).isPresent();
        Assertions.assertThat(fromDB.get())
                .usingRecursiveComparison()
                .isEqualTo(created);
    }

    @Test
    void deleteRulePositive() {
        Random rand = new Random();
        Rules rule = rulesRepository.findAll().get(rand.nextInt(3));
        Long ruleId = rule.getId();
        HttpEntity<Rules> request = new HttpEntity<>(rule);
        ResponseEntity<Rules> forEntity = restTemplate
                .exchange("/rules/" + ruleId, HttpMethod.DELETE,
                        request, Rules.class);
        System.out.println(forEntity.getBody());
        Assertions.assertThat(forEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(rulesRepository.findAll()).doesNotContain(forEntity.getBody());
        Assertions.assertThat(forEntity.getBody()).isNotIn(rulesRepository.findAll());
        Assertions.assertThat(rulesRepository.findById(forEntity.getBody().getId())).isEmpty();
        Assertions.assertThat(rulesRepository.findById(forEntity.getBody().getId()))
                .isNotIn(rulesRepository.findAll());
    }


    @Test
    void deleteRuleNegative() {
        Random rand = new Random();
        Rules student = rulesRepository.findAll().get(rand.nextInt(3));
        HttpEntity<Rules> request = new HttpEntity<>(student);
        ResponseEntity<String> forEntity = restTemplate
                .exchange("/rules/" + -1, HttpMethod.DELETE,
                        request, String.class);
        Assertions.assertThat(forEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        Assertions.assertThat(forEntity.getBody()).isEqualTo("Rule with id = %d not found".formatted(-1));
    }
}
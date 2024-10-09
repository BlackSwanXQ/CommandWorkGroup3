package com.example.CommandWorkGroup3.dao;


import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class TransactionDao {
    private final JdbcTemplate transactionJdbcTemplate;



    public TransactionDao(JdbcTemplate transactionJdbcTemplate) {
        this.transactionJdbcTemplate = transactionJdbcTemplate;
    }

    public Boolean checkCondition(UUID clientId, String condition) {
        return transactionJdbcTemplate.queryForObject(condition, Boolean.class, clientId);
    }


}

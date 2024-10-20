package com.example.CommandWorkGroup3.dao;


import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class TransactionDao {
    private final JdbcTemplate transactionJdbcTemplate;

    private static final String USER_OF_QUERY = """
            SELECT EXISTS (
            	SELECT 1 FROM USERS u\s
            	JOIN TRANSACTIONS t ON t.USER_ID = u.ID\s
            	JOIN PRODUCTS p ON p.ID = t.PRODUCT_ID\s
            	WHERE u.ID = ?1 AND p."TYPE" = ?2\s
            ) AS res;
            """;

    private static final String ACTIVE_USER_OF_QUERY = """
            SELECT (
            	SELECT COUNT(*) FROM USERS u\s
            	JOIN TRANSACTIONS t ON t.USER_ID = u.ID\s
            	JOIN PRODUCTS p ON p.ID = t.PRODUCT_ID\s
            	WHERE u.ID = ?1 AND p."TYPE" = ?2\s
            ) > 5 AS res;
            """;

    private static final String TRANSACTION_SUM_COMPARE_QUERY = """
            SELECT (
            	SELECT COALESCE(SUM(t.AMOUNT), 0)\s
            	FROM USERS u\s
            	JOIN TRANSACTIONS t ON t.USER_ID = u.ID AND t."TYPE" = ?2
            	JOIN PRODUCTS p ON p.ID = t.PRODUCT_ID AND p."TYPE" = ?3\s
            	WHERE u.ID = ?1\s
            ) %s ?4 AS res;
            """;

    private static final String TRANSACTION_SUM_COMPARE_DEPOSIT_WITHDRAW_QUERY = """
            SELECT (
            	SELECT COALESCE(SUM(t.AMOUNT), 0)\s
            	FROM USERS u\s
            	JOIN TRANSACTIONS t ON t.USER_ID = u.ID\s
            	AND t."TYPE" = 'DEPOSIT'\s
            	JOIN PRODUCTS p ON p.ID = t.PRODUCT_ID AND p."TYPE" = ?2\s
            	WHERE u.ID = ?1\s
            ) %s (
            	SELECT COALESCE(SUM(t.AMOUNT), 0)\s
            	FROM USERS u\s
            	JOIN TRANSACTIONS t ON t.USER_ID = u.ID AND t."TYPE" = 'WITHDRAW'\s
            	JOIN PRODUCTS p ON p.ID = t.PRODUCT_ID AND p."TYPE" = ?2\s
            	WHERE u.ID = ?1\s
            ) AS res;
            """;

    public TransactionDao(JdbcTemplate transactionJdbcTemplate) {
        this.transactionJdbcTemplate = transactionJdbcTemplate;
    }

    public Boolean checkCondition(UUID clientId, String condition) {
        return transactionJdbcTemplate.queryForObject(condition, Boolean.class, clientId);
    }

    public Boolean executeUserOfQuery(UUID clientId, String productType) {
        return transactionJdbcTemplate.queryForObject(USER_OF_QUERY, Boolean.class, clientId, productType);
    }

    public Boolean executeActiveUserOfQuery(UUID clientId, String productType) {
        return transactionJdbcTemplate.queryForObject(ACTIVE_USER_OF_QUERY, Boolean.class, clientId, productType);
    }

    public Boolean executeTransactionSumCompareQuery(UUID clientId, String transactionType, String productType, int toCompare, String operation) {
        return transactionJdbcTemplate.queryForObject(
                String.format(TRANSACTION_SUM_COMPARE_QUERY, operation),
                Boolean.class,
                clientId, transactionType, productType, toCompare
        );
    }

    public Boolean executeTransactionSumCompareWithdrawQuery(UUID clientId, String productType, String operation) {
        return transactionJdbcTemplate.queryForObject(
                String.format(TRANSACTION_SUM_COMPARE_DEPOSIT_WITHDRAW_QUERY, operation),
                Boolean.class,
                clientId, productType
        );
    }


}

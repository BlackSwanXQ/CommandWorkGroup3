package com.example.CommandWorkGroup3.repository;

import com.example.CommandWorkGroup3.exceptions.RecommendationsNotFoundException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class RecommendationsRepository {
    private final JdbcTemplate jdbcTemplate;

    public RecommendationsRepository(@Qualifier("recommendationsJdbcTemplate") JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int getRandomTransactionAmount(UUID user) {
        Integer result = jdbcTemplate.queryForObject(
                "SELECT amount FROM transactions t " +
                        "WHERE t.user_id = ? LIMIT 1",
                Integer.class,
                user);
        return result != null ? result : 0;

    }

    public Boolean getInvest500(UUID user) {
            Boolean result = jdbcTemplate.queryForObject(
                    "SELECT EXISTS (\n" +
                            "                            SELECT 1 FROM TRANSACTIONS t\n" +
                            "                            JOIN PRODUCTS p ON p.ID = t.PRODUCT_ID\n" +
                            "                            WHERE t.USER_ID = ? AND p.TYPE = 'DEBIT'\n" +
                            "                             )\n" +
                            "                             AND NOT EXISTS (\n" +
                            "                             SELECT 1 FROM TRANSACTIONS t\n" +
                            "                             JOIN PRODUCTS p ON p.ID = t.PRODUCT_ID\n" +
                            "                             WHERE t.USER_ID = ? AND p.TYPE = 'INVEST'\n" +
                            "                             )\n" +
                            "                             AND (\n" +
                            "                             SELECT COALESCE(SUM(t.AMOUNT), 0) FROM TRANSACTIONS t\n" +
                            "                             JOIN PRODUCTS p ON p.ID = t.PRODUCT_ID AND p.TYPE = 'SAVING'\n" +
                            "                             WHERE t.USER_ID = ? AND (SELECT SUM(t.AMOUNT) FROM TRANSACTIONS)\n" +
                            "                             ) > 1000",
                    Boolean.class,
                    user,
                    user,
                    user);
            return result;
    }

    public Boolean getTopSaving(UUID user) {
        Boolean result = jdbcTemplate.queryForObject(
                "SELECT EXISTS (\n" +
                        "SELECT 1 FROM TRANSACTIONS t \n" +
                        "JOIN PRODUCTS p ON p.ID = t.PRODUCT_ID \n" +
                        "WHERE t.USER_ID = ? AND p.TYPE = 'DEBIT'\n" +
                        ")\n" +
                        "AND ((SELECT COALESCE(SUM(t.AMOUNT), 0) FROM TRANSACTIONS t\n" +
                        "JOIN PRODUCTS p ON p.ID = t.PRODUCT_ID AND p.TYPE = 'DEBIT'\n" +
                        "WHERE t.USER_ID = ? \n" +
                        "AND (SELECT SUM(t.AMOUNT) FROM TRANSACTIONS) >= 50000)\n" +
                        "OR (SELECT COALESCE(SUM(t.AMOUNT), 0) FROM TRANSACTIONS t \n" +
                        "JOIN PRODUCTS p ON p.ID = t.PRODUCT_ID AND p.TYPE = 'SAVING'\n" +
                        "WHERE t.USER_ID = ?\n" +
                        "AND (SELECT SUM(t.AMOUNT) FROM TRANSACTIONS) >= 50000)\n" +
                        ")\n" +
                        "AND (SELECT COALESCE(SUM(t.AMOUNT), 0) FROM TRANSACTIONS t\n" +
                        "JOIN PRODUCTS p ON p.ID = t.PRODUCT_ID AND t.TYPE = 'DEPOSIT' AND p.TYPE = 'DEBIT'\n" +
                        "WHERE t.USER_ID = ?) >\n" +
                        "(SELECT COALESCE(SUM(t.AMOUNT), 0) FROM TRANSACTIONS t\n" +
                        "JOIN PRODUCTS p ON p.ID = t.PRODUCT_ID AND t.TYPE = 'WITHDRAW' AND p.TYPE = 'DEBIT'\n" +
                        "WHERE t.USER_ID = ?)",
                Boolean.class,
                user,
                user,
                user,
                user,
                user);
        return result;

    }
        public Boolean getSimpleCredit(UUID user) {
            Boolean result = jdbcTemplate.queryForObject(
                    "SELECT NOT EXISTS (\n" +
                            "SELECT 1 FROM TRANSACTIONS t \n" +
                            "JOIN PRODUCTS p ON p.ID = t.PRODUCT_ID \n" +
                            "WHERE t.USER_ID = ? AND p.TYPE = 'CREDIT'\n" +
                            ")\n" +
                            "AND (SELECT COALESCE(SUM(t.AMOUNT), 0) FROM TRANSACTIONS t \n" +
                            "JOIN PRODUCTS p ON p.ID = t.PRODUCT_ID AND p.TYPE = 'DEBIT' AND t.TYPE = 'DEPOSIT'\n" +
                            "WHERE t.USER_ID = ?) >\n" +
                            "(SELECT COALESCE(SUM(t.AMOUNT), 0) FROM TRANSACTIONS t \n" +
                            "JOIN PRODUCTS p ON p.ID = t.PRODUCT_ID AND p.TYPE = 'DEBIT' AND t.TYPE = 'WITHDRAW'\n" +
                            "WHERE t.USER_ID = ?)\n" +
                            "AND (\n" +
                            "SELECT COALESCE (SUM(t.AMOUNT), 0) FROM TRANSACTIONS t\n" +
                            "JOIN PRODUCTS p ON p.ID = t.PRODUCT_ID AND p.TYPE = 'DEBIT'\n" +
                            "WHERE t.USER_ID = ?\n" +
                            "AND (SELECT SUM(t.AMOUNT) FROM TRANSACTIONS) > 100000)",
                    Boolean.class,
                    user,
                    user,
                    user,
                    user);

            return  result;
    }
}

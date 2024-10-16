package com.example.CommandWorkGroup3.repository;

import com.example.CommandWorkGroup3.interfaces.RulesRepository;
import com.example.CommandWorkGroup3.services.RulesService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Repository
public class RecommendationsRepository {
    private final JdbcTemplate jdbcTemplate;
    private final RulesRepository rulesRepository;

    public RecommendationsRepository(@Qualifier("recommendationsJdbcTemplate") JdbcTemplate jdbcTemplate, RulesRepository rulesRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.rulesRepository = rulesRepository;
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
                        "SELECT 1 FROM TRANSACTIONS t\n" +
                        "JOIN PRODUCTS p ON p.ID = t.PRODUCT_ID\n" +
                        "WHERE t.USER_ID = ? AND p.TYPE = 'DEBIT'\n" +
                        ")\n" +
                        "AND NOT EXISTS (\n" +
                        "SELECT 1 FROM TRANSACTIONS t\n" +
                        "JOIN PRODUCTS p ON p.ID = t.PRODUCT_ID\n" +
                        "WHERE t.USER_ID = ? AND p.TYPE = 'INVEST'\n" +
                        ")\n" +
                        "AND (\n" +
                        "SELECT COALESCE(SUM(t.AMOUNT), 0) FROM TRANSACTIONS t\n" +
                        "JOIN PRODUCTS p ON p.ID = t.PRODUCT_ID AND p.TYPE = 'SAVING'\n" +
                        "WHERE t.USER_ID = ? AND (SELECT SUM(t.AMOUNT) FROM TRANSACTIONS)\n" +
                        ") > 1000",
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
                        "JOIN PRODUCTS p ON p.ID = t.PRODUCT_ID AND p.TYPE = 'DEBIT' AND t.TYPE = 'WITHDRAW'\n" +
                        "WHERE t.USER_ID = ?\n" +
                        "AND (SELECT SUM(t.AMOUNT) FROM TRANSACTIONS) > 100000)",
                Boolean.class,
                user,
                user,
                user,
                user);
        return result;

    }

    public Boolean getSimpleCreditFromDB(UUID user) {
        String argumentsUserOf = rulesRepository.findByQuery("USER_OF").getArguments();
        String argumentsActiveUserOf = rulesRepository.findByQuery("ACTIVE_USER_OF").getArguments();
        List<String> argumentsTransactionSumCompare = Arrays.stream(rulesRepository.findByQuery("TRANSACTION_SUM_COMPARE").getArguments().split(", ")).toList();
        List<String> argumentsTransactionSumCompareDepositWithdraw = Arrays.stream(rulesRepository.findByQuery("TRANSACTION_SUM_COMPARE_DEPOSIT_WITHDRAW").getArguments().split(", ")).toList();

        Boolean result = jdbcTemplate.queryForObject(
                "SELECT NOT EXISTS (\n" +
                        "SELECT 1 FROM TRANSACTIONS t \n" +
                        "JOIN PRODUCTS p ON p.ID = t.PRODUCT_ID \n" +
                        "WHERE t.USER_ID = ? AND p.TYPE = ?\n" +
                        ")\n" +
                        "AND (SELECT COUNT(*) >= 5 FROM TRANSACTIONS t \n" +
                        "JOIN PRODUCTS p ON p.ID = t.PRODUCT_ID \n" +
                        "WHERE t.USER_ID = ? AND p.TYPE = ?)\n" +
                        "AND (SELECT COALESCE(SUM(t.AMOUNT), 0) FROM TRANSACTIONS t \n" +
                        "JOIN PRODUCTS p ON p.ID = t.PRODUCT_ID AND p.TYPE = ? AND t.TYPE = 'DEPOSIT'\n" +
                        "WHERE t.USER_ID = ? ) >\n" +
                        "(SELECT COALESCE(SUM(t.AMOUNT), 0) FROM TRANSACTIONS t \n" +
                        "JOIN PRODUCTS p ON p.ID = t.PRODUCT_ID AND p.TYPE = ? AND t.TYPE = 'WITHDRAW'\n" +
                        "WHERE t.USER_ID = ?)\n" +
                        "AND (\n" +
                        "SELECT COALESCE (SUM(t.AMOUNT), 0) FROM TRANSACTIONS t\n" +
                        "JOIN PRODUCTS p ON p.ID = t.PRODUCT_ID AND p.TYPE = ? AND t.TYPE = ?\n" +
                        "WHERE t.USER_ID = ?\n" +
                        "AND (SELECT SUM(t.AMOUNT) FROM TRANSACTIONS) > ?)",
                Boolean.class,
                user,
                argumentsUserOf,
                user,
                argumentsActiveUserOf,
                argumentsTransactionSumCompareDepositWithdraw.get(0),
                user,
                argumentsTransactionSumCompareDepositWithdraw.get(0),
                user,
                argumentsTransactionSumCompare.get(0),
                argumentsTransactionSumCompare.get(1),
                user,
                Integer.parseInt(argumentsTransactionSumCompare.get(3))

        );
        return result;
    }


}

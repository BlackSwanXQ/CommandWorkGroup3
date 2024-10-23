package com.example.CommandWorkGroup3.repository;

import com.example.CommandWorkGroup3.exceptions.RuleNotFoundException;
import com.example.CommandWorkGroup3.interfaces.RulesRepository;
import com.example.CommandWorkGroup3.services.RulesService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.*;

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
        List<String> args = new ArrayList<String>();
        args.add(rulesRepository.findByQuery("USER_OF").get().getArguments());
        args.add(rulesRepository.findByQuery("ACTIVE_USER_OF").get().getArguments());
        args.addAll(Arrays.stream(rulesRepository.findByQuery("TRANSACTION_SUM_COMPARE").get().getArguments().split(", ")).toList());
        args.addAll(Arrays.stream(rulesRepository.findByQuery("TRANSACTION_SUM_COMPARE_DEPOSIT_WITHDRAW").get().getArguments().split(", ")).toList());
//        System.out.println(args);

//        String argumentsUserOf = rulesRepository.findByQuery("USER_OF").get().getArguments();
//        System.out.println(argumentsUserOf);
//        String argumentsActiveUserOf = rulesRepository.findByQuery("ACTIVE_USER_OF").get().getArguments();
//        System.out.println(argumentsActiveUserOf);
//        List<String> argumentsTransactionSumCompare = Arrays.stream(rulesRepository.findByQuery("TRANSACTION_SUM_COMPARE").get().getArguments().split(", ")).toList();
//        System.out.println(argumentsTransactionSumCompare);
//        List<String> argumentsTransactionSumCompareDepositWithdraw = Arrays.stream(rulesRepository.findByQuery("TRANSACTION_SUM_COMPARE_DEPOSIT_WITHDRAW").get().getArguments().split(", ")).toList();
//        System.out.println(argumentsTransactionSumCompareDepositWithdraw);

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
                args.get(0),
                user,
                args.get(1),
                args.get(6), // argumentsTransactionSumCompareDepositWithdraw.get(0),
                user,
                args.get(6), //argumentsTransactionSumCompareDepositWithdraw.get(0),
                user,
                args.get(2), //argumentsTransactionSumCompare.get(0),
                args.get(3), //argumentsTransactionSumCompare.get(1),
                user,
                Integer.parseInt(args.get(5)) //Integer.parseInt(argumentsTransactionSumCompare.get(3))

        );
        return result;
    }
}

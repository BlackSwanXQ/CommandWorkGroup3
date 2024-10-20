package com.example.CommandWorkGroup3.rules;

import com.example.CommandWorkGroup3.dao.TransactionDao;
import com.fasterxml.jackson.annotation.JsonTypeName;

import java.util.UUID;

@JsonTypeName("TRANSACTION_SUM_COMPARE_DEPOSIT_WITHDRAW")
public class TransactionSumCompareDepositWithdrawRule extends AbstractRule{

    @Override
    public boolean apply(UUID clientId, TransactionDao dao) {
        if (getArguments().length != 2) throw new RuntimeException("Неверное количество аргументов");
        boolean result = dao.executeTransactionSumCompareWithdrawQuery(clientId, getArguments()[0], getArguments()[1]);
        return getNegate() ? !result : result;
    }
}

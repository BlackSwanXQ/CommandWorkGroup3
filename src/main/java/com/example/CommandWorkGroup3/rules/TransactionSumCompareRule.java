package com.example.CommandWorkGroup3.rules;

import com.example.CommandWorkGroup3.dao.TransactionDao;
import com.fasterxml.jackson.annotation.JsonTypeName;

import java.util.UUID;

@JsonTypeName("TRANSACTION_SUM_COMPARE")
public class TransactionSumCompareRule extends AbstractRule {

    @Override
    public boolean apply(UUID clientId, TransactionDao dao) {
        if (getArguments().length != 4) throw new RuntimeException("Неверное количество аргументов");
        boolean result = dao.executeTransactionSumCompareQuery(clientId, getArguments()[1], getArguments()[0], Integer.valueOf(getArguments()[3]), getArguments()[2]);
        return getNegate() ? !result : result;
    }
}

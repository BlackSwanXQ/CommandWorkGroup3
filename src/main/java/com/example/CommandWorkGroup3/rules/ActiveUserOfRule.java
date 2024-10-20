package com.example.CommandWorkGroup3.rules;

import com.example.CommandWorkGroup3.dao.TransactionDao;
import com.fasterxml.jackson.annotation.JsonTypeName;

import java.util.UUID;

@JsonTypeName("ACTIVE_USER_OF")
public class ActiveUserOfRule extends AbstractRule {

    @Override
    public boolean apply(UUID clientId, TransactionDao dao) {
        if (getArguments().length != 1) throw new RuntimeException("Неверное количество аргументов");
        boolean result = dao.executeActiveUserOfQuery(clientId, getArguments()[0]);
        return getNegate() ? !result : result;
    }
}

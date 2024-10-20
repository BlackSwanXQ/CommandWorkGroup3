package com.example.CommandWorkGroup3.rules;

import com.example.CommandWorkGroup3.dao.TransactionDao;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;


import java.util.UUID;

@JsonTypeInfo(
        include = JsonTypeInfo.As.EXISTING_PROPERTY,
        use = JsonTypeInfo.Id.NAME,
        property = "query",
        visible = true
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = UserOfRule.class, name = "USER_OF"),
        @JsonSubTypes.Type(value = ActiveUserOfRule.class, name = "ACTIVE_USER_OF"),
        @JsonSubTypes.Type(value = ActiveUserOfRule.class, name = "TRANSACTION_SUM_COMPARE"),
        @JsonSubTypes.Type(value = ActiveUserOfRule.class, name = "TRANSACTION_SUM_COMPARE_DEPOSIT_WITHDRAW")
})
@Getter
@Setter
public abstract class AbstractRule {
    private String query;
    @NotEmpty private String[] arguments;
    private Boolean negate = Boolean.FALSE;

    public abstract boolean apply(UUID clientId, TransactionDao dao);
}

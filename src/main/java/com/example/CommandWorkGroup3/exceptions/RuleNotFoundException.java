package com.example.CommandWorkGroup3.exceptions;

public class RuleNotFoundException extends NotFoundException{

    public RuleNotFoundException(Long id)  {
        super(id);
    }

    @Override
    public String getMessage() {
        return "Rule with id = %d not found".formatted(getMes());
    }
}

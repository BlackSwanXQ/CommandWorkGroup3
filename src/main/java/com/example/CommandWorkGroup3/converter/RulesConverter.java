package com.example.CommandWorkGroup3.converter;

import com.example.CommandWorkGroup3.rules.AbstractRule;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lombok.RequiredArgsConstructor;


import java.util.Set;

@Converter
@RequiredArgsConstructor
public class RulesConverter implements AttributeConverter<Set<AbstractRule>, String> {

    private final ObjectMapper objectMapper;

    @Override
    public String convertToDatabaseColumn(Set<AbstractRule> rules) {
        try {
            return objectMapper.writeValueAsString(rules);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Set<AbstractRule> convertToEntityAttribute(String s) {

        try {
            TypeReference<Set<AbstractRule>> typeRef = new TypeReference<>() {};
            return objectMapper.readValue(s, typeRef);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}

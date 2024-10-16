package com.example.CommandWorkGroup3.services;

import com.example.CommandWorkGroup3.entity.Rules;
import com.example.CommandWorkGroup3.exceptions.RuleNotFoundException;
import com.example.CommandWorkGroup3.interfaces.RulesRepository;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class RulesService {
    private RulesRepository rulesRepository;
    RulesService(RulesRepository rulesRepository) {
        this.rulesRepository = rulesRepository;
    }

    public Rules addRule(Rules rule) {
        return rulesRepository.save(rule);
    }

    public Rules deleteRule(Long id) {
        Rules rule = rulesRepository.findById(id)
                        .orElseThrow(() -> new RuleNotFoundException(id));
        rulesRepository.delete(rule);
        return rule;
    }

    public List<Rules> getAllRules() {
        return rulesRepository.findAll();
    }
}

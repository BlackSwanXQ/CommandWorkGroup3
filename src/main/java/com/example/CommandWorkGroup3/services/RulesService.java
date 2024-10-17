package com.example.CommandWorkGroup3.services;

import com.example.CommandWorkGroup3.entity.Rules;
import com.example.CommandWorkGroup3.exceptions.RuleNotFoundException;
import com.example.CommandWorkGroup3.interfaces.RulesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class RulesService {
    private final RulesRepository rulesRepository;
    private final Logger logger = LoggerFactory.getLogger(RulesService.class);

    RulesService(RulesRepository rulesRepository) {
        this.rulesRepository = rulesRepository;
    }

    public Rules addRule(Rules rule) {
        logger.info("Was invoked method for create rule with query: " + rule.getQuery());
        return rulesRepository.save(rule);
    }

    public Rules deleteRule(Long id) {
        logger.info("Was invoked method for delete rule with id: " + id);
        Rules rule = rulesRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("There is not Student with id = " + id);
                    return new RuleNotFoundException(id);
                });
        rulesRepository.delete(rule);
        logger.debug("Rule with id {} was removed", rule.getId());
        return rule;
    }

    public List<Rules> getAllRules() {
        logger.info("Was invoked method for get all rules");
        return rulesRepository.findAll();
    }
}

package com.example.CommandWorkGroup3.controller;

import com.example.CommandWorkGroup3.entity.Rules;
import com.example.CommandWorkGroup3.interfaces.RulesRepository;
import com.example.CommandWorkGroup3.services.RulesService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.util.Arrays.asList;

@RestController
@RequestMapping("/rules")
public class RulesController {

    private final RulesService rulesService;

    public RulesController(RulesService rulesService) {
        this.rulesService = rulesService;
    }

    @PostMapping
    public Rules addRule(@RequestBody Rules rule) {
        return rulesService.addRule(rule);
    }

    @DeleteMapping("/{id}")
    public Rules deleteRule(@PathVariable Long id) {
        return rulesService.deleteRule(id);
    }

    @GetMapping("/all")
    public List<Rules> getAllRules() {
        return rulesService.getAllRules();
    }


}

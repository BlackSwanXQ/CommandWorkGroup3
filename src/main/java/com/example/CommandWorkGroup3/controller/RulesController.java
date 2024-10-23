package com.example.CommandWorkGroup3.controller;

import com.example.CommandWorkGroup3.entity.Rules;
import com.example.CommandWorkGroup3.interfaces.RulesRepository;
import com.example.CommandWorkGroup3.services.RulesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.util.Arrays.asList;

@RestController
@RequestMapping("/rules")
@Tag(name = "Rules API", description = "API for management rules")
public class RulesController {

    private final RulesService rulesService;

    public RulesController(RulesService rulesService) {
        this.rulesService = rulesService;
    }

    @PostMapping
    @Operation(summary = "Add a rule", description = "Adds a rule")
    @ApiResponse(responseCode = "200", description = "Successful response")
    public Rules addRule(@RequestBody Rules rule) {
        return rulesService.addRule(rule);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a rule", description = "Deletes a rule")
    @ApiResponse(responseCode = "200", description = "Successful response")
    public Rules deleteRule(@PathVariable Long id) {
        return rulesService.deleteRule(id);
    }

    @GetMapping("/all")
    @Operation(summary = "Get all rules",description = "Gets all the rules")
    @ApiResponse(responseCode = "200",description = "Successful response")
    public List<Rules> getAllRules() {
        return rulesService.getAllRules();
    }




}

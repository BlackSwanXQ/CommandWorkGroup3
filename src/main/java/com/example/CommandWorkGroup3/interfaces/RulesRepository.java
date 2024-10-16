package com.example.CommandWorkGroup3.interfaces;

import com.example.CommandWorkGroup3.entity.Rules;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface RulesRepository extends JpaRepository<Rules, Long> {
Rules findByQuery(String query);
}

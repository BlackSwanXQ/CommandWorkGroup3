package com.example.CommandWorkGroup3.interfaces;

import com.example.CommandWorkGroup3.entity.Rules;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RulesRepository extends JpaRepository<Rules, Long> {
Optional<Rules> findByQuery(String query);
}

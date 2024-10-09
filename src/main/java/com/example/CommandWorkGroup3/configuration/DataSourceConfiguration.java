package com.example.CommandWorkGroup3.configuration;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfiguration {
    @Bean(name = "transactionDataSource")
    public DataSource transactionDataSource(@Value("${application.transaction-db.url}") String transactionUrl) {
        var dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(transactionUrl);
        dataSource.setDriverClassName("org.h2.Driver");
        dataSource.setReadOnly(true);
        return dataSource;
    }

    @Bean(name = "transactionJdbcTemplate")
    public JdbcTemplate transactionJdbcTemplate(
            @Qualifier("transactionDataSource") DataSource dataSource
    ) {
        return new JdbcTemplate(dataSource);
    }

    @Bean(name = "recommendationDataSource")
    public DataSource recommendationDataSource(@Value("${application.recommendation-db.url}") String recommendationUrl) {
        var dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(recommendationUrl);
        dataSource.setDriverClassName("org.h2.Driver");
        dataSource.setReadOnly(true);
        return dataSource;
    }

    @Bean(name = "recommendationJdbcTemplate")
    public JdbcTemplate recommendationJdbcTemplate(
            @Qualifier("recommendationDataSource") DataSource dataSource
    ) {
        return new JdbcTemplate(dataSource);
    }
}

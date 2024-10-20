package com.example.CommandWorkGroup3.configuration;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableJpaRepositories(basePackages = "com.example.CommandWorkGroup3.repository")
//@EnableTransactionManagement
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

    @Primary
    @Bean(name = "defaultDataSource")
    public DataSource defaultDataSource(DataSourceProperties properties) {
        return properties.initializeDataSourceBuilder().build();
    }

    @Bean(name = "recommendationJdbcTemplate")
    public JdbcTemplate recommendationJdbcTemplate(
            @Qualifier("defaultDataSource") DataSource dataSource
    ) {
        return new JdbcTemplate(dataSource);
    }


}

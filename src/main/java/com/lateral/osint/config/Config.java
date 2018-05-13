package com.lateral.osint.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

@Configuration
public class Config {

    @Value("${db.url}")
    private String url;

    @PostConstruct
    private void init() {
        dataSource = new DriverManagerDataSource(url, System.getProperty("db_user"), System.getProperty("db_password"));
    }

    private DataSource dataSource;

    @Bean(name = "jdbc")
    public NamedParameterJdbcTemplate jdbc() {
        return new NamedParameterJdbcTemplate(dataSource);
    }
}

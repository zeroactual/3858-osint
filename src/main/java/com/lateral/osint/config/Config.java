package com.lateral.osint.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

@Configuration
@EnableWebSocketMessageBroker
public class Config implements WebSocketMessageBrokerConfigurer {

    @Value("${db.url}")
    private String url;

    @PostConstruct
    private void init() {
        dataSource = new DriverManagerDataSource(url, System.getProperty("db_user"), System.getProperty("db_password"));
    }

    private DataSource dataSource;

    @Value("${scheduled.github_enabled}")
    private boolean github_enabled;

    @Value("${scheduled.maps_enabled}")
    private boolean maps_enabled;

    @Bean(name = "github_enabled")
    public boolean isGithub_enabled() {
        return github_enabled;
    }

    @Bean(name = "maps_enabled")
    public boolean isMaps_enabled() {
        return maps_enabled;
    }

    @Bean(name = "jdbc")
    public NamedParameterJdbcTemplate jdbc() {
        return new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/updates");
        config.setApplicationDestinationPrefixes("/ws");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/gs-guide-websocket").withSockJS();
    }
}

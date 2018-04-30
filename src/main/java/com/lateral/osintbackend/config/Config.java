package com.lateral.osintbackend.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;
import java.util.Properties;

@Configuration
@EnableWebSocketMessageBroker
public class Config implements WebSocketMessageBrokerConfigurer {

    private static Connection conn;

    @Value("${db.url}")
    private String url;
    @Value("${db.user}")
    private String user;
    @Value("${db.password}")
    private String password;

    @Qualifier("db")
    @Bean
    public Connection getConnection() {
        try {
            Properties props = new Properties();
            props.setProperty("user", user);
            props.setProperty("password", password);
            props.setProperty("ssl", "false");
            conn = DriverManager.getConnection(url, props);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
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



    public void initialize() {
        Yaml yaml = new Yaml();

        try {
            System.out.println(yaml.dump(yaml.load(new FileInputStream(new File("queries.yml")))));


            Map<String, Map<String, String>> values = (Map<String, Map<String, String>>) yaml.load(new FileInputStream(new File("hello_world.yaml")));

            for (String key : values.keySet()) {
                Map<String, String> subValues = values.get(key);
                System.out.println(key);

                for (String subValueKey : subValues.keySet()) {
                    System.out.println(String.format("\t%s = %s",
                            subValueKey, subValues.get(subValueKey)));
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}

package com.lateral.osintbackend.scheduled;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

@Service
public class Indexer {

    private static Connection conn;

    @Qualifier("db")
    @Bean
    public Connection getConnection() {
        try {
            String url = "jdbc:postgresql://localhost/postgres";
            Properties props = new Properties();
            props.setProperty("user", "wes");
            props.setProperty("password", "T_1*AqR2y");
            props.setProperty("ssl", "true");
            conn = DriverManager.getConnection(url, props);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    @Scheduled(fixedRate = 5000)
    public void indexRepos() throws SQLException {
        if (conn == null) getConnection();
        conn.nativeSQL("SELECT * FROM languages");
    }
}

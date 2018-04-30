package com.lateral.osintbackend.scheduled;

import com.lateral.osintbackend.dao.github.RepoDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.sql.Connection;

@Service
public class Indexer {

    @Autowired
    private Connection db;

    @Value("${db.select_star}")
    private String sql;

    @Scheduled(fixedRate = 3600000)
    public void indexRepos() throws Exception {
        RepoDao.run();
    }
}

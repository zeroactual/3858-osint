package com.lateral.osint.github.dao.db;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.lateral.osint.github.model.db.Repo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Lazy
@Service
public class RepoDao {
    @Autowired
    private NamedParameterJdbcTemplate jdbc;

    public void insertRepos(List<Repo> repos) {
        List<Map<String, String>> params = Lists.newArrayList();
        for (Repo r: repos) {
            Map<String, String> paramMap = Maps.newHashMap();
            paramMap.put("name", String.valueOf(r.getName()));
            params.add(paramMap);
        }
        jdbc.batchUpdate("INSERT INTO repos(name) VALUES(:name) ON CONFLICT DO NOTHING", (Map<String, String>[]) params.toArray());
    }

    public void insertRepo(Repo repo) {
        Map<String, String> paramMap = Maps.newHashMap();
        paramMap.put("name", String.valueOf(repo.getName()));
        jdbc.update("INSERT INTO repos(name) VALUES(:name) ON CONFLICT DO NOTHING", paramMap);
    }
}

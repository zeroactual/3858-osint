package com.lateral.osint.github.dao.db;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.lateral.osint.github.model.db.Language;
import com.lateral.osint.github.model.db.Org;
import com.lateral.osint.github.model.db.Repo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Lazy
@Service
public class RepoOrgsDao {
    @Autowired
    private NamedParameterJdbcTemplate jdbc;

    public void insert(Repo repo, Org org) {
        // Clear out old data
        delete(repo);

        Map<String, String> paramMap = Maps.newHashMap();
        paramMap.put("repo", String.valueOf(repo.getName()));
        paramMap.put("org", String.valueOf(org.getName()));
        jdbc.update("INSERT INTO org_repo(repo, name) VALUES(:repo, :org)", paramMap);
    }

    public void delete(Repo repo) {
        Map<String, String> paramMap = Maps.newHashMap();
        paramMap.put("repo", String.valueOf(repo.getName()));
        jdbc.update("DELETE FROM org_repo WHERE repo = :repo", paramMap);
    }
}

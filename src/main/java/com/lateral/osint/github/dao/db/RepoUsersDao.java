package com.lateral.osint.github.dao.db;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.lateral.osint.github.model.db.Language;
import com.lateral.osint.github.model.db.Repo;
import com.lateral.osint.github.model.db.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Lazy
@Service
public class RepoUsersDao {
    @Autowired
    private NamedParameterJdbcTemplate jdbc;

    public void insert(Repo repo, List<User> users) {
        // Clear out old data
        delete(repo);

        List<Map<String, String>> params = Lists.newArrayList();
        for (User u: users) {
            Map<String, String> paramMap = Maps.newHashMap();
            paramMap.put("repo", String.valueOf(repo.getName()));
            paramMap.put("user", String.valueOf(u.getName()));
            params.add(paramMap);
        }
        Map[] vals = params.toArray(new Map[params.size()]);
        jdbc.batchUpdate("INSERT INTO repo_users(repo, \"user\") VALUES(:repo, :user)", vals);
    }

    public void delete(Repo repo) {
        Map<String, String> paramMap = Maps.newHashMap();
        paramMap.put("repo", String.valueOf(repo.getName()));
        jdbc.update("DELETE FROM repo_users WHERE repo = :repo", paramMap);
    }
}

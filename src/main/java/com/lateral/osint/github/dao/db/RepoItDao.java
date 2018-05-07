package com.lateral.osint.github.dao.db;

import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

@Lazy
@Service
public class RepoItDao {

    @Autowired
    private NamedParameterJdbcTemplate jdbc;

    public void updatePosition(long i) {
        Map<String, Object> paramMap = Maps.newHashMap();
        paramMap.put("last_pos", BigInteger.valueOf(i));
        jdbc.update("UPDATE repo_it SET last_pos = :last_pos", paramMap);
    }

    public long getPosition() {
        List results = jdbc.queryForList("SELECT last_pos FROM repo_it WHERE id = 1", Maps.newHashMap());
        Map<String, Object> result = (Map<String, Object>) results.get(0);
        return Long.parseLong(result.get("last_pos").toString());
    }
}

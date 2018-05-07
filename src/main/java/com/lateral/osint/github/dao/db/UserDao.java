package com.lateral.osint.github.dao.db;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.lateral.osint.github.model.db.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Lazy
@Service
public class UserDao {
    @Autowired
    private NamedParameterJdbcTemplate jdbc;

    public void insertUsers(List<User> users) {
        List<Map<String, String>> params = Lists.newArrayList();
        for (User u: users) {
            Map<String, String> paramMap = Maps.newHashMap();
            paramMap.put("name", String.valueOf(u.getName()));
            paramMap.put("location", u.getLocation());
            paramMap.put("raw_location", String.valueOf(u.getRaw_location()!= null ? u.getRaw_location().substring(0, u.getRaw_location().length() > 128 ? 127 : u.getRaw_location().length()) : null));
            paramMap.put("company", String.valueOf(u.getCompany()));
            params.add(paramMap);
        }
        Map[] vals = params.toArray(new Map[params.size()]);
        jdbc.batchUpdate("INSERT INTO users(name, location, raw_location, company) VALUES(:name, :location, :raw_location, :company) ON CONFLICT(name) DO UPDATE SET location = :location, raw_location = :raw_location", vals);
    }
}

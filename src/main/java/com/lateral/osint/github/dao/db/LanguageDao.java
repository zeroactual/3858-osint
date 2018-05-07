package com.lateral.osint.github.dao.db;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.lateral.osint.github.model.db.Language;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Lazy
@Service
public class LanguageDao {
    @Autowired
    private NamedParameterJdbcTemplate jdbc;

    public void insertLanguages(List<Language> languages) {
        List<Map<String, String>> params = Lists.newArrayList();
        for (Language l: languages) {
            Map<String, String> paramMap = Maps.newHashMap();
            paramMap.put("name", String.valueOf(l.getName()));
            params.add(paramMap);
        }

        Map[] vals = params.toArray(new Map[params.size()]);
        jdbc.batchUpdate("INSERT INTO languages(name) VALUES(:name) ON CONFLICT DO NOTHING", vals);
    }
}

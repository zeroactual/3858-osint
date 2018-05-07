package com.lateral.osint.github.dao.db;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.lateral.osint.github.model.db.Org;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Lazy
@Service
public class OrgDao {
    @Autowired
    private NamedParameterJdbcTemplate jdbc;

    public void insertOrgs(List<Org> orgs) {
        List<Map<String, String>> params = Lists.newArrayList();
        for (Org o: orgs) {
            Map<String, String> paramMap = Maps.newHashMap();
            paramMap.put("name", String.valueOf(o.getName()));
            paramMap.put("location", o.getLocation());
            paramMap.put("raw_location", String.valueOf(o.getRaw_location()!= null ? o.getRaw_location().substring(0, o.getRaw_location().length() > 128 ? 127 : o.getRaw_location().length()) : null));
            params.add(paramMap);
        }

        Map[] vals = params.toArray(new Map[params.size()]);
        jdbc.batchUpdate("INSERT INTO orgs(name, location) VALUES(:name, :location) ON CONFLICT(name) DO UPDATE SET location = :location, raw_location = :raw_location", vals);
    }

    public void insertOrg(Org org) {
        Map<String, String> paramMap = Maps.newHashMap();
        paramMap.put("name", String.valueOf(org.getName()));
        paramMap.put("location", org.getLocation());
        paramMap.put("raw_location", String.valueOf(org.getRaw_location()!= null ? org.getRaw_location().substring(0, org.getRaw_location().length() > 128 ? 127 : org.getRaw_location().length()) : null));
        jdbc.update("INSERT INTO orgs(name, raw_location) VALUES(:name, :raw_location) ON CONFLICT(name) DO UPDATE SET location = :location, raw_location = :raw_location", paramMap);
    }
}

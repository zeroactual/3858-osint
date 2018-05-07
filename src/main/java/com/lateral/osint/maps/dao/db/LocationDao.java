package com.lateral.osint.maps.dao.db;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.lateral.osint.maps.model.db.Location;
import com.lateral.osint.maps.model.db.LocationOwnerType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Lazy
@Service
public class LocationDao {

    @Autowired
    private NamedParameterJdbcTemplate jdbc;

    public List<LocationOwnerType> getTopUnmapped() {
        List<LocationOwnerType> locations = Lists.newArrayList();
        List<Map<String, Object>> results = jdbc.queryForList("SELECT * FROM (SELECT 'user' AS type, name, raw_location FROM users WHERE location IS NULL AND (raw_location IS NOT NULL AND raw_location <> '') UNION ALL SELECT 'org' AS type, name, raw_location FROM orgs WHERE location IS NULL AND (raw_location IS NOT NULL AND raw_location <> '')) as u LIMIT 50", Maps.newHashMap());
        for (Map m : results){
            LocationOwnerType location = new LocationOwnerType();
            String value = (String) m.get("raw_location");
            String type = (String) m.get("type");
            String name = (String) m.get("name");
            location.setCountry(value);
            location.setType(type);
            location.setOwner(name);
            if (value != null && value.length() > 0) {
                locations.add(location);
            }
        }
        return locations;
    }

    public void insertLocations(List<Location> locations) {
        List<Map<String, String>> params = Lists.newArrayList();
        for (Location l: locations) {
            Map<String, String> paramMap = Maps.newHashMap();
            paramMap.put("name", String.valueOf(l.getCountry()));
            params.add(paramMap);
        }

        Map[] vals = params.toArray(new Map[params.size()]);
        jdbc.batchUpdate("INSERT INTO locations(name) VALUES(:name) ON CONFLICT DO NOTHING", vals);
    }
}
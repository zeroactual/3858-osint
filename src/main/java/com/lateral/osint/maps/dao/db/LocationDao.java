package com.lateral.osint.maps.dao.db;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.lateral.osint.maps.model.db.Location;
import com.lateral.osint.maps.model.db.LocationOwnerType;
import com.lateral.osint.model.MapData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Lazy
@Service
public class LocationDao {

    @Autowired
    private NamedParameterJdbcTemplate jdbc;

    public List<LocationOwnerType> getTopUnmappedUsers() {
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

    public List<Location> getTopUnmappedLocations() {
        List<Location> locations = Lists.newArrayList();
        List<Map<String, Object>> results = jdbc.queryForList("SELECT * FROM locations WHERE alpha_2 IS NULL AND name <> 'search_error' LIMIT 50", Maps.newHashMap());
        for (Map m : results){
            Location location = new Location();
            String name = (String) m.get("name");
            String alpha_2 = (String) m.get("alpha_2");
            location.setCountry(name);
            location.setAlpha_2(alpha_2);
            locations.add(location);
        }
        return locations;
    }

    public List<MapData> getMapData() {
        List<MapData> locations = Lists.newArrayList();
        List<Map<String, Object>> results = jdbc.queryForList("SELECT q.country, q.alpha_2, COUNT(q.name) FROM (SELECT locations.name AS country, locations.alpha_2 AS alpha_2, users.name FROM locations INNER JOIN users ON locations.name = users.location UNION ALL SELECT locations.name AS country, locations.alpha_2 AS alpha_2, orgs.name FROM locations INNER JOIN orgs ON locations.name = orgs.location) q WHERE q.country <> 'search_error' GROUP BY q.country, q.alpha_2 ORDER BY COUNT(q.name) DESC", Maps.newHashMap());
        for (Map m : results){
            MapData location = new MapData();
            String alpha_2 = (String) m.get("alpha_2");
            Long count = (long) m.get("count");

            location.setAlpha_2(alpha_2.toLowerCase());
            location.setCount(count);
            locations.add(location);
        }
        return locations;
    }

    public void insertLocations(List<Location> locations) {
        List<Map<String, String>> params = Lists.newArrayList();
        for (Location l: locations) {
            Map<String, String> paramMap = Maps.newHashMap();
            paramMap.put("name", String.valueOf(l.getCountry()));
            paramMap.put("alpha_2", String.valueOf(l.getAlpha_2()));
            params.add(paramMap);
        }

        Map[] vals = params.toArray(new Map[params.size()]);
        jdbc.batchUpdate("INSERT INTO locations(name, alpha_2) VALUES(:name, :alpha_2) ON CONFLICT(name) DO UPDATE SET alpha_2  = :alpha_2", vals);
    }
}
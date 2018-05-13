package com.lateral.osint.service;

import com.google.common.collect.Lists;
import com.lateral.osint.maps.dao.db.LocationDao;
import com.lateral.osint.model.MapData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;

@Lazy
@Slf4j
@Service
public class MapService {

    @Autowired
    private LocationDao locationDao;

    public List<List<Object>> getMapData() {
        List<MapData> locations = locationDao.getMapData();
        log.info("Retrieved: {} locations", locations.size());
        List<List<Object>> listList = Lists.newArrayList();
        for (MapData l: locations) {
            List<Object> innerList = Lists.newArrayList();
            innerList.add(l.getAlpha_2());
            innerList.add(l.getCount());
            listList.add(innerList);
        }
        return listList;
    }

    public String print() {
        StringBuilder sb = new StringBuilder();
        sb.append('[');
        List<List<Object>> list = getMapData();
        int c = 0;
        int max = list.size();
        for (List<Object> ll : list) {
            sb.append('[');
            int i = 0;
            for (Object o: ll) {
                if (i == 0) {
                    sb.append('\'');
                    sb.append((String) o);
                    sb.append('\'');
                    sb.append(',');
                } else {
                    sb.append(o);
                }
                i++;
            }
            sb.append(']');
            if (c < max - 1) {
                sb.append(',');
            }
            c++;
        }
        sb.append(']');
        return sb.toString();
    }
}

package com.lateral.osint.maps.service;

import com.google.common.collect.Lists;
import com.lateral.osint.github.dao.db.OrgDao;
import com.lateral.osint.github.dao.db.UserDao;
import com.lateral.osint.github.model.db.Org;
import com.lateral.osint.github.model.db.User;
import com.lateral.osint.maps.dao.db.LocationDao;
import com.lateral.osint.maps.dao.maps.MapsDao;
import com.lateral.osint.maps.model.db.Location;
import com.lateral.osint.maps.model.db.LocationOwnerType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Slf4j
@Lazy
@Service
public class LocationMappingService {

    @Autowired
    private LocationDao locationDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private OrgDao orgDao;

    @Autowired
    private MapsDao mapsDao;

    public boolean mapGoogleToLocation() {
        List<LocationOwnerType> raw_locations = Lists.newArrayList();
        raw_locations.addAll(locationDao.getTopUnmappedUsers());
        List<Location> locations = Lists.newArrayList();


        List<User> users = Lists.newArrayList();
        List<Org> orgs = Lists.newArrayList();

        log.info("Indexing Users: {}", raw_locations.size());
        for (LocationOwnerType l: raw_locations) {
            try {
                Location location = mapsDao.getLocation(l.getCountry());
                if (location == null) {
                    location = new Location();
                    location.setCountry("search_error");
                }
                locations.add(location);
                if (l.getType().equals("user")) {
                    log.info("Location mapped to user: {}, location: {}", l.getOwner(), l.getCountry());
                    User u = new User();
                    u.setName(l.getOwner());
                    u.setLocation(location.getCountry());
                    u.setRaw_location(l.getCountry());
                    users.add(u);
                } else {
                    log.info("Location mapped to org: {}, location: {}", l.getOwner(), l.getCountry());
                    Org o = new Org();
                    o.setName(l.getOwner());
                    o.setLocation(location.getCountry());
                    o.setRaw_location(l.getCountry());
                    orgs.add(o);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

//        List<Location> unmapped_locations = Lists.newArrayList();
//        unmapped_locations.addAll(locationDao.getTopUnmappedLocations());
//        log.info("Indexing Locations: {}", unmapped_locations.size());
//        for (Location l: unmapped_locations) {
//            try {
//                Location location = mapsDao.getLocation(l.getCountry());
//                if (location != null) {
//                    if (location.getAlpha_2() != null) {
//                        locations.add(location);
//                        log.info("Alpha_2: {}, mapped to location: {}", location.getAlpha_2(), location.getCountry());
//                    } else log.error("Country: {} has no Alpha_2", location.getCountry());
//                } else log.error("Country: {} is null", l.getCountry());
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }

        if (!locations.isEmpty()) {
            locationDao.insertLocations(locations);
            if (!orgs.isEmpty())
                orgDao.insertOrgs(orgs);
            if (!users.isEmpty())
                userDao.insertUsers(users);
        }
        return true;
    }
}

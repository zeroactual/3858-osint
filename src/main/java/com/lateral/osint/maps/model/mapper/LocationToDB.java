package com.lateral.osint.maps.model.mapper;

import com.lateral.osint.maps.model.db.Location;
import com.lateral.osint.maps.model.maps.LocationJson;

import java.util.List;

public class LocationToDB {
    public static Location map(LocationJson from) {
        Location location = new Location();
        LocationJson.Result result = from.getResults().get(0);
        List<LocationJson.AddressComponent> components = result.getAddress_components();
        for (LocationJson.AddressComponent ac: components) {
            for (String s: ac.getTypes()) {
                if (s.equals("country")) {
                    location.setCountry(ac.getLong_name());
                    location.setAlpha_2(ac.getShort_name());
                    return location;
                }
            }
        }
        return null;
    }
}

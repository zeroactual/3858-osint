package com.lateral.osint.maps.scheduled;

import com.lateral.osint.maps.service.LocationMappingService;
import com.lateral.osint.service.BoolService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class LocationIndexer {

    @Autowired
    private BoolService boolService;

    @Autowired
    private LocationMappingService service;

    @Scheduled(fixedRate = 1500)
    public void indexLocations() {
        if (boolService.getMaps_enabled()) {
            log.warn("Running location indexer");
            service.mapGoogleToLocation();
        }
        else {
            log.warn("Not set to run");
        }
    }
}

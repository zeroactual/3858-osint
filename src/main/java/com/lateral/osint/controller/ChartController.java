package com.lateral.osint.controller;

import com.lateral.osint.service.MapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ChartController {

    @Autowired
    private MapService mapService;

    @GetMapping("/worldmap/print")
    public String printMapData() {
       return mapService.print();
    }

    @GetMapping("/worldmap")
    public List<List<Object>> getMapData() {
        return mapService.getMapData();
    }
}

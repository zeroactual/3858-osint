package com.lateral.osint.controller;

import com.lateral.osint.dao.StatsDao;
import com.lateral.osint.model.Counts;
import com.lateral.osint.service.BoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
public class Index {

    @Autowired
    private BoolService boolService;

    @Autowired
    private StatsDao statsDao;

    @RequestMapping("/")
    public String index(Map<String, Object> model) {
        Counts counts = statsDao.getCounts();
        model.put("repo_count", counts.getRepos());
        model.put("language_count", counts.getLanguages());
        model.put("country_count", counts.getCountries());
        model.put("user_count", counts.getUsers());
        model.put("language_country", statsDao.getLanguageCounts());
        model.put("users", statsDao.getUserLanguageCounts());

        return "index";
    }

    @RequestMapping("/map")
    public String map(Map<String, Object> model) {
        return "worldmap";
    }


    @RequestMapping("/toggle-index")
    public String toggleIndex() {
        boolService.toggleGithub();
        return "redirect:/";
    }

    @RequestMapping("/toggle-mapping")
    public String toggleMapping() {
        boolService.toggleMaps();
        return "redirect:/";
    }
}

package com.lateral.osint.dao;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.lateral.osint.model.Counts;
import com.lateral.osint.model.LanguageCounts;
import com.lateral.osint.model.UserLanguageCounts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Lazy
@Service
public class StatsDao {

    @Autowired
    private NamedParameterJdbcTemplate jdbc;

    public Counts getCounts() {
        List<Map<String, Object>> results = jdbc.queryForList("SELECT * FROM (SELECT COUNT(*) AS countries FROM locations) countries, (SELECT COUNT (*) AS users FROM (SELECT NAME FROM orgs UNION ALL SELECT NAME FROM users) q) users, (SELECT COUNT (*) AS repos FROM repos) repos, (SELECT COUNT (*) AS languages FROM languages) languages", Maps.newHashMap());
        Counts counts = new Counts();

        for (Map m : results){
            long repos = (long) m.get("repos");
            long users = (long) m.get("users");
            long languages = (long) m.get("languages");
            long countries = (long) m.get("countries");

            counts.setCountries(countries);
            counts.setLanguages(languages);
            counts.setRepos(repos);
            counts.setUsers(users);
        }
        return counts;
    }

    public List<LanguageCounts> getLanguageCounts() {
        List<LanguageCounts> counts = Lists.newArrayList();
        List<Map<String, Object>> results = jdbc.queryForList("SELECT q.country, q.lang, COUNT(q.*) AS repos FROM\n" +
                "(\n" +
                "SELECT l.name AS country, lang.name AS lang FROM locations l\n" +
                "  INNER JOIN users u ON u.location = l.name\n" +
                "  INNER JOIN repo_users u2 ON u.name = u2.\"user\"\n" +
                "  INNER JOIN repos r ON u2.repo = r.name\n" +
                "  FULL JOIN repo_languages l2 ON r.name = l2.repo\n" +
                "  INNER JOIN languages lang ON l2.language = lang.name\n" +
                "  WHERE l.name IS NOT NULL\n" +
                "UNION ALL\n" +
                "SELECT l.name AS country, lang.name AS lang FROM locations l\n" +
                "  INNER JOIN orgs u ON u.location = l.name\n" +
                "  INNER JOIN org_repo u2 ON u.name = u2.name\n" +
                "  INNER JOIN repos r ON u2.repo = r.name\n" +
                "  FULL JOIN repo_languages l2 ON r.name = l2.repo\n" +
                "  INNER JOIN languages lang ON l2.language = lang.name\n" +
                "  WHERE l.name IS NOT NULL\n" +
                ")q GROUP BY q.country, q.lang\n" +
                "ORDER BY COUNT(q.lang) DESC", Maps.newHashMap());
        for (Map m : results){
            LanguageCounts languageCounts = new LanguageCounts();
            String country = (String) m.get("country");
            String lang = (String) m.get("lang");
            long repos = (long) m.get("repos");

            languageCounts.setCountry(country);
            languageCounts.setLanguage(lang);
            languageCounts.setRepos(repos);
            counts.add(languageCounts);
        }
        return counts;
    }

    public List<UserLanguageCounts> getUserLanguageCounts() {
        List<UserLanguageCounts> counts = Lists.newArrayList();
        List<Map<String, Object>> results = jdbc.queryForList("SELECT u.name, u.location,COUNT(l.*) AS langs, COUNT(r.*) AS repos FROM locations l\n" +
                "  INNER JOIN users u ON u.location = l.name\n" +
                "  INNER JOIN repo_users u2 ON u.name = u2.\"user\"\n" +
                "  INNER JOIN repos r ON u2.repo = r.name\n" +
                "  FULL JOIN repo_languages l2 ON r.name = l2.repo\n" +
                "  INNER JOIN languages lang ON l2.language = lang.name\n" +
                "  WHERE u.name IS NOT NULL\n" +
                "GROUP BY u.name ORDER BY COUNT(l.*) DESC", Maps.newHashMap());
        for (Map m : results){
            UserLanguageCounts languageCounts = new UserLanguageCounts();
            String name = (String) m.get("name");
            String country = (String) m.get("location");
            long langs = (long) m.get("langs");
            long repos = (long) m.get("repos");
            languageCounts.setName(name);
            languageCounts.setCountry(country);
            languageCounts.setLangs(langs);
            languageCounts.setRepos(repos);
            counts.add(languageCounts);
        }
        return counts;
    }
}

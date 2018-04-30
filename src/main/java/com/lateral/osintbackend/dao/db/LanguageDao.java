package com.lateral.osintbackend.dao.db;

import com.google.common.collect.Lists;
import com.lateral.osintbackend.model.Language;
import com.lateral.osintbackend.model.Org;
import com.lateral.osintbackend.model.Repo;
import com.lateral.osintbackend.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Lazy
@Service
public class LanguageDao {

    @Autowired
    private Connection db;

    public void insertLanguage() {


//        db.prepareStatement()
    }

    public List<Language> getLanguages() throws SQLException {
        List<Language> languages = Lists.newArrayList();
        ResultSet rs = db.prepareStatement("").executeQuery();
        while (rs.next()) {
            Language language = new Language();
            language.setName(rs.getString("name"));
            languages.add(language);
        }

        return languages;
    }

    public List<Language> getLanguages(Repo repo) throws SQLException {
        List<Language> languages = Lists.newArrayList();
        ResultSet rs = db.prepareStatement("").executeQuery();
        while (rs.next()) {
            Language language = new Language();
            language.setName(rs.getString("name"));
            languages.add(language);
        }

        return languages;
    }

    public List<Language> getLanguages(Org org) throws SQLException {
        List<Language> languages = Lists.newArrayList();
        ResultSet rs = db.prepareStatement("").executeQuery();
        while (rs.next()) {
            Language language = new Language();
            language.setName(rs.getString("name"));
            languages.add(language);
        }

        return languages;
    }

    public List<Language> getLanguages(User user) throws SQLException {
        List<Language> languages = Lists.newArrayList();
        ResultSet rs = db.prepareStatement("").executeQuery();
        while (rs.next()) {
            Language language = new Language();
            language.setName(rs.getString("name"));
            languages.add(language);
        }

        return languages;
    }
}

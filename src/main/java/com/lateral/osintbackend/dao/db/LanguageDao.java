package com.lateral.osintbackend.daos;

import com.lateral.osintbackend.model.Language;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class LanguageDao {

    @Autowired
    private Connection db;

    public void insertLanguage() {


        db.prepareStatement()
    }

    public List<Language> getLanguages() throws SQLException {

        ResultSet rs = db.prepareStatement("").executeQuery();
        while (rs.next()) {
            Language language = new Language();
            language.setName(rs.getString("FNAME"));
        }
    }
}

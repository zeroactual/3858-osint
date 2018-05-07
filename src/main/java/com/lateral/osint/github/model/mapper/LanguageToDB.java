package com.lateral.osint.github.model.mapper;

import com.lateral.osint.github.model.db.Language;
import com.lateral.osint.github.model.github.LanguageJson;

public class LanguageToDB {

    public static Language map(LanguageJson from) {
        Language language = new Language();
        language.setName(from.getName());
        return language;
    }
}

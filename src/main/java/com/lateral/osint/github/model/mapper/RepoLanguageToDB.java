package com.lateral.osint.github.model.mapper;

import com.google.common.collect.Lists;
import com.lateral.osint.github.model.db.Language;
import com.lateral.osint.github.model.db.Repo;
import com.lateral.osint.github.model.db.RepoLanguages;

import java.util.List;

public class RepoLanguageToDB {
    public static List<RepoLanguages> map(Repo repo, List<Language> languages) {
        List<RepoLanguages> list = Lists.newArrayList();
        for (Language l: languages) {
            RepoLanguages row = new RepoLanguages();
            row.setLanguage(l.getName());
            row.setRepo(repo.getName());
            list.add(row);
        }
        return list;
    }
}

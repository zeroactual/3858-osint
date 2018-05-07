package com.lateral.osint.github.model.mapper;

import com.lateral.osint.github.model.db.Repo;
import com.lateral.osint.github.model.github.RepoJson;

public class RepoToDB {

    public static Repo map(RepoJson from) {
        Repo repo = new Repo();
        repo.setName(from.getName());
        return repo;
    }


}

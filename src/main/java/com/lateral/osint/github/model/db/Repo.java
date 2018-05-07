package com.lateral.osint.github.model.db;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Repo {
    private String name;
    private List<User> contributors;
    private List<Language> languages;
}

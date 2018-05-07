package com.lateral.osint.github.model.db;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Org {
    private String name;
    private String location;
    private String raw_location;
    private List<Repo> repos;
}

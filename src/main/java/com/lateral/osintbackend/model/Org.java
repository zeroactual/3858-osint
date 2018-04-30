package com.lateral.osintbackend.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Org {
    private String name;
    private String location;
    private List<Repo> repos;
}

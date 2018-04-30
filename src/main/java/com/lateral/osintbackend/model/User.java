package com.lateral.osintbackend.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class User {
    private String name;
    private String location;
    private String company;
    private List<Repo> repos;
}

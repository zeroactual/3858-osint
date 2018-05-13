package com.lateral.osint.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserLanguageCounts {
    private String name;
    private String country;
    private long langs;
    private long repos;
}

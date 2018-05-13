package com.lateral.osint.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LanguageCounts {
    private String country;
    private String language;
    private long repos;
}

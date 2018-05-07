package com.lateral.osint.github.model.github;

import com.google.api.client.util.Key;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RepoJson {

    @Key
    private long id;
    @Key
    private String name;
    @Key
    private OwnerJson owner;
    @Key
    private boolean fork;
}


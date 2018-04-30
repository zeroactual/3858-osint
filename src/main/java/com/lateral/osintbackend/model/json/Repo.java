package com.lateral.osintbackend.model.json;

import com.google.api.client.util.Key;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Repo {

    @Key
    private long id;
    @Key
    private String name;
    @Key
    private Owner owner;
    @Key
    private boolean fork;
}


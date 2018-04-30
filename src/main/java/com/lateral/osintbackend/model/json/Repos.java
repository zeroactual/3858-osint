package com.lateral.osintbackend.model.json;

import com.google.api.client.util.Key;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Repos {

    @Key
    private List<Repo> repos;
}


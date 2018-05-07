package com.lateral.osint.github.model.github;

import com.google.api.client.util.Key;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OwnerJson {

    @Key
    private String login;
    @Key
    private String type;
}
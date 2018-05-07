package com.lateral.osint.github.model.github;

import com.google.api.client.util.Key;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserJson {

    @Key
    private String login;
    @Key
    private String location;
}
package com.lateral.osintbackend.model.json;

import com.google.api.client.util.Key;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Owner {

    @Key
    private String login;
    @Key
    private String type;
}
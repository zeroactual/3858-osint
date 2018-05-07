package com.lateral.osint.maps.model.db;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LocationOwnerType {
    private String country;
    private String type;
    private String owner;
}

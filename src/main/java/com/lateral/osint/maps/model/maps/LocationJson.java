package com.lateral.osint.maps.model.maps;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.google.api.client.util.Key;
import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class LocationJson {

    @Key
    private List<Result> results;

    @Getter
    @Setter
    public static class Result {
        @Key
        private List<AddressComponent> address_components;
    }

    @Getter
    @Setter
    public static class AddressComponent {
        @Key
        private String long_name;
        @Key
        private String short_name;
        @Key
        private List<String> types;
    }
}
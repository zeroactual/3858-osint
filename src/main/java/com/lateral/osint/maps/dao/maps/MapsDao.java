package com.lateral.osint.maps.dao.maps;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.JsonObjectParser;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.lateral.osint.maps.model.db.Location;
import com.lateral.osint.maps.model.mapper.LocationToDB;
import com.lateral.osint.maps.model.maps.LocationJson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URLEncoder;

@Slf4j
@Lazy
@Service
public class MapsDao {

    private final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
    private final JsonFactory JSON_FACTORY = new JacksonFactory();
    private final HttpRequestFactory REQUEST_FACTORY = HTTP_TRANSPORT.createRequestFactory(request -> request.setParser(new JsonObjectParser(JSON_FACTORY)));

    public Location getLocation(String query) throws IOException {
        String url_str = String.format("https://maps.googleapis.com/maps/api/geocode/json?address=%s&key=%s&components=country", URLEncoder.encode(String.valueOf(query), "UTF-8"), System.getProperty("maps_api_key"));
        GenericUrl url = new GenericUrl(url_str);
        log.info("Attempting url: {}", url_str);
        HttpRequest request = REQUEST_FACTORY.buildGetRequest(url);

        LocationJson locations = request.execute().parseAs(LocationJson.class);

        if (locations.getResults().isEmpty()) {
            log.info("No locations found");
        } else {
            return LocationToDB.map(locations);
        }
        return null;
    }

}
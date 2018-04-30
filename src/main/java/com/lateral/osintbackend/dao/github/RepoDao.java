package com.lateral.osintbackend.dao.github;

import com.google.api.client.http.*;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.JsonObjectParser;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.common.collect.Lists;
import com.lateral.osintbackend.model.json.Repo;

import java.util.List;


public class RepoDao {

    private static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
    private static final JsonFactory JSON_FACTORY = new JacksonFactory();



    public static void run() throws Exception {
        HttpRequestFactory requestFactory = HTTP_TRANSPORT.createRequestFactory(request -> request.setParser(new JsonObjectParser(JSON_FACTORY)));

        long next = 0;
        while(true) {
            try {
                GenericUrl url = new GenericUrl(String.format("https://api.github.com/repositories?since=%s", next));
                HttpRequest request = requestFactory.buildGetRequest(url);
                List<Repo> repos = Lists.newArrayList(request.execute().parseAs(Repo[].class));
                request.setRequestMethod("POST");
                if (repos.isEmpty()) {
                    System.out.println("No repos found.");
                } else {
                    System.out.println(repos.size() + " repos found:");
                    for (Repo repo : repos) {
                        System.out.println();
                        System.out.println("-----------------------------------------------");
                        System.out.println("ID: " + repo.getId());
                        System.out.println("Name: " + repo.getName());
                        System.out.println("Owner: " + repo.getOwner().getLogin());
                        System.out.println("Owner Type: " + repo.getOwner().getType());
                        next = repo.getId();
                    }
                }
            } catch (HttpResponseException ex) {
                break;
            }
        }
    }
}

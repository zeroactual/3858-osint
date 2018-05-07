package com.lateral.osint.github.dao.github;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.client.http.*;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.GenericJson;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.JsonObjectParser;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.common.collect.Lists;
import com.lateral.osint.github.dao.db.*;
import com.lateral.osint.github.model.db.Language;
import com.lateral.osint.github.model.db.Org;
import com.lateral.osint.github.model.db.User;
import com.lateral.osint.github.model.github.OrgJson;
import com.lateral.osint.github.model.github.OwnerJson;
import com.lateral.osint.github.model.github.RepoJson;
import com.lateral.osint.github.model.github.UserJson;
import com.lateral.osint.github.model.mapper.OrgToDB;
import com.lateral.osint.github.model.mapper.RepoToDB;
import com.lateral.osint.github.model.mapper.UserToDB;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;

@Slf4j
@Lazy
@Service
public class GithubRepo {

    @Autowired
    private RepoDao repoDao;

    @Autowired
    private RepoItDao repoItDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private LanguageDao languageDao;

    @Autowired
    private RepoLanguagesDao repoLanguagesDao;

    @Autowired
    private RepoUsersDao repoUsersDao;

    @Autowired
    private RepoOrgsDao repoOrgsDao;

    @Autowired
    private OrgDao orgDao;

    private final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
    private final JsonFactory JSON_FACTORY = new JacksonFactory();
    private final HttpRequestFactory REQUEST_FACTORY = HTTP_TRANSPORT.createRequestFactory(request -> request.setParser(new JsonObjectParser(JSON_FACTORY)));

    public void run() throws Exception {

        long next = repoItDao.getPosition();
        while(true) {
            try {
                String url_str = String.format("https://api.github.com/repositories?since=%s", URLEncoder.encode(String.valueOf(next), "UTF-8"));
                GenericUrl url = new GenericUrl(url_str);
                HttpRequest request = REQUEST_FACTORY.buildGetRequest(url);
                request.getHeaders().setAuthorization(String.format("token %s", System.getProperty("github_api_token")));
                log.info("Attempting url: {}", url_str);
                List<RepoJson> repos = Lists.newArrayList(request.execute().parseAs(RepoJson[].class));
                if (repos.isEmpty()) {
                    log.info("No repos found");
                } else {
                    log.info("Found {} repos", repos.size());
                    for (RepoJson repo : repos) {
                        OwnerJson owner = repo.getOwner();
                        List<Language> languages = getLanguages(owner.getLogin(), repo.getName());

                        User user;
                        Org org = null;
                        List<User> users = Lists.newArrayList();

                        // Get owner details
                        if (owner.getType().equals("User")) {
                            user = getUser(owner.getLogin());
                            users.add(user);
                            log.info("Owner added, login: {}, location: {}, raw_location: {}, company: {}", user.getName(), user.getLocation(), user.getRaw_location(), user.getCompany());
                        } else {
                            org = getOrg(owner.getLogin());
                            log.info("Owner added, login: {}, location: {}, raw_location: {}, company: {}", org.getName(), org.getLocation(), org.getRaw_location());
                        }

                        // Get top collaborators
                        try {
                            String contrib_url_str = String.format("https://api.github.com/repos/%s/%s/contributors", repo.getName(), owner.getLogin());
                            GenericUrl urlContrib = new GenericUrl(contrib_url_str);
                            log.info("Attempting url: {}", contrib_url_str);
                            HttpRequest requestContrib = REQUEST_FACTORY.buildGetRequest(urlContrib);
                            requestContrib.getHeaders().setAuthorization(String.format("token %s", System.getProperty("github_api_token")));
                            List<UserJson> contributors = Lists.newArrayList(requestContrib.execute().parseAs(UserJson[].class));
                            for (UserJson contributor : contributors) {
                                User contrib = getUser(contributor.getLogin());
                                users.add(contrib);
                                log.info("Contributor added, login: {}, location: {}, raw_location: {}, company: {}", contrib.getName(), contrib.getLocation(), contrib.getRaw_location(), contrib.getCompany());
                            }
                        } catch (HttpResponseException | NullPointerException ignored) {}


                        repoDao.insertRepo(RepoToDB.map(repo));

                        // Do last
                        if (!users.isEmpty()) {
                            userDao.insertUsers(users);
                            repoUsersDao.insert(RepoToDB.map(repo), users);
                        }
                        if (org != null) {
                            orgDao.insertOrg(org);
                            repoOrgsDao.insert(RepoToDB.map(repo), org);
                        }

                        if (!languages.isEmpty()) {
                            languageDao.insertLanguages(languages);
                            repoLanguagesDao.insert(RepoToDB.map(repo), languages);
                        }

                        next = repo.getId();
                        repoItDao.updatePosition(next);
                        Thread.sleep(1000);
                    }
                }
            } catch (HttpResponseException ex) {
                ex.printStackTrace();
                break;
            }
        }
    }

    List<Language> getLanguages(String owner, String repo) {
        List<Language> languages = Lists.newArrayList();
        try {
            // Populate languages
            String url_str = String.format("https://api.github.com/repos/%s/%s/languages", URLEncoder.encode(owner, "UTF-8"), URLEncoder.encode(repo, "UTF-8"));
            log.info("Attempting url: {}", url_str);
            GenericUrl url = new GenericUrl(url_str);
            HttpRequest request = REQUEST_FACTORY.buildGetRequest(url);
            request.getHeaders().setAuthorization(String.format("token %s", System.getProperty("github_api_token")));

            GenericJson json = request.execute().parseAs(GenericJson.class);
            HashMap<String, String> result = new ObjectMapper().readValue(json.toPrettyString(), HashMap.class);

            for (String s: result.keySet()) {
                log.info("Repo: {}, Language added: {}", repo, s);
                Language l = new Language();
                l.setName(s);
                languages.add(l);
            }
            return languages;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return languages;
    }

    User getUser(String login) {
        try {
            String url_str = String.format("https://api.github.com/users/%s", URLEncoder.encode(login, "UTF-8"));
            GenericUrl url = new GenericUrl(url_str);
            log.info("Attempting url: {}", url_str);
            HttpRequest request = REQUEST_FACTORY.buildGetRequest(url);
            request.getHeaders().setAuthorization(String.format("token %s", System.getProperty("github_api_token")));

            UserJson user = request.execute().parseAs(UserJson.class);
            log.info("User login: {}, location: {}", user.getLogin(), user.getLocation());
            return UserToDB.map(user);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    Org getOrg(String login) {
        try {
            String url_str = String.format("https://api.github.com/orgs/%s", URLEncoder.encode(login, "UTF-8"));
            GenericUrl url = new GenericUrl(url_str);
            log.info("Attempting url: {}", url_str);
            HttpRequest request = REQUEST_FACTORY.buildGetRequest(url);
            request.getHeaders().setAuthorization(String.format("token %s", System.getProperty("github_api_token")));

            OrgJson org = request.execute().parseAs(OrgJson.class);
            log.info("Org login: {}, location: {}", org.getLogin(), org.getLocation());
            return OrgToDB.map(org);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}

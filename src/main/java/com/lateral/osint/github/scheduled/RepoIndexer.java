package com.lateral.osint.github.scheduled;

import com.lateral.osint.github.dao.github.GithubRepo;
import com.lateral.osint.service.BoolService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class RepoIndexer {

    @Autowired
    private BoolService boolService;

    @Autowired
    private GithubRepo repo;

    @Scheduled(fixedRate = 3600000)
    public void indexRepos() throws Exception {
        if (boolService.getGithub_enabled()) {
            log.warn("Running");
            repo.run();
        }
        else {
            log.warn("Not set to run");
        }
    }
}

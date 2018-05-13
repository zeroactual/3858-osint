package com.lateral.osint.service;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Getter
@Lazy
@Slf4j
@Service
public class BoolService {

    @Value("${scheduled.github_enabled}")
    private Boolean github_enabled;

    @Value("${scheduled.maps_enabled}")
    private Boolean maps_enabled;

    public void toggleGithub() {
        github_enabled = !github_enabled;
    }

    public void toggleMaps() {
        maps_enabled = !maps_enabled;
    }
}

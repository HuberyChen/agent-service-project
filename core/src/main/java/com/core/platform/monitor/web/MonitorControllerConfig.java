package com.core.platform.monitor.web;

import org.springframework.context.annotation.Bean;

public class MonitorControllerConfig {

    @Bean
    public StatusController statusController() {
        return new StatusController();
    }

}

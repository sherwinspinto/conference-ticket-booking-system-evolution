package com.sherwin.conference.bookingsystem.infrastructure.config;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SchedulerServiceConfig {
  @Bean(name = "scheduledExecutorService")
  public ScheduledExecutorService scheduledExecutorService() {
    return Executors.newScheduledThreadPool(4);
  }
}

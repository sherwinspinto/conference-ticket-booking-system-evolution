package com.sherwin.conference.bookingsystem.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

@Configuration
public class ExecutorConfig {

  @Bean(name = "taskScheduler")
  public ScheduledExecutorService taskScheduler () {
    return Executors.newSingleThreadScheduledExecutor();
  }

  @Bean(name = "virtualExecutor")
  public ExecutorService virtualExecutor () {
    return Executors.newVirtualThreadPerTaskExecutor();
  }
}

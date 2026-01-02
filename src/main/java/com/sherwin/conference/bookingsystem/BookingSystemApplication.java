package com.sherwin.conference.bookingsystem;

import com.sherwin.conference.bookingsystem.domain.DomainService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableRetry
@EnableScheduling
@ComponentScan(
    basePackages = {
      "com.sherwin.conference"
    },
    includeFilters = {
      @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = DomainService.class)
    })
public class BookingSystemApplication {
  public static void main(String[] args) {
    SpringApplication.run(BookingSystemApplication.class, args);
  }
}

package com.sherwin.conference.bookingsystem;

import com.sherwin.conference.bookingsystem.domain.DomainService;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

@SpringBootApplication
@ComponentScan(
    basePackages = {"com.sherwin.conference"},
    includeFilters = {
      @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = DomainService.class)
    })
public class TestConfig {}

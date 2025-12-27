package com.sherwin.conference.bookingsystem.config;

import com.sherwin.conference.bookingsystem.infrastructure.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class ExpiryScheduler {
  @Autowired
  private TicketService ticketService;

  @Scheduled(fixedRate = 60000) // Every minute
  public void expireReservations() {
    ticketService.expireOldReservations();
  }
}

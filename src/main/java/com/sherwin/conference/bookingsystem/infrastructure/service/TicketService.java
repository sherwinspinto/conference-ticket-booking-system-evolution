package com.sherwin.conference.bookingsystem.infrastructure.service;

import com.sherwin.conference.bookingsystem.domain.feature.TicketDomainService;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TicketService {
  private final TicketDomainService ticketDomainService;

  public TicketService(TicketDomainService ticketDomainService) {
    this.ticketDomainService = ticketDomainService;
  }

  @Retryable(
      value = {ObjectOptimisticLockingFailureException.class},
      maxAttempts = 5,
      backoff = @Backoff(delay = 100) // 100 ms delay between retries
      )
  @Transactional
  public String reserveTicket(Long talkId, String userEmail) {
    return ticketDomainService.reserveTicket(talkId, userEmail);
  }

  @Transactional
  public String confirmPayment(Long ticketId) {
    return ticketDomainService.confirmPayment(ticketId);
  }
}

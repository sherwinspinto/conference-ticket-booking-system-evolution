package com.sherwin.conference.bookingsystem.service;

import com.sherwin.conference.bookingsystem.domain.ReservationResult;
import com.sherwin.conference.bookingsystem.domain.Ticket;
import com.sherwin.conference.bookingsystem.domain.mapper.Mapper;
import com.sherwin.conference.bookingsystem.entity.TalkEntity;
import com.sherwin.conference.bookingsystem.entity.TicketEntity;
import com.sherwin.conference.bookingsystem.repository.TalkRepository;
import com.sherwin.conference.bookingsystem.repository.TicketRepository;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TicketService {
  private final TicketRepository ticketRepository;

  private final TalkRepository talkRepository;

  @Autowired
  public TicketService(TicketRepository ticketRepository, TalkRepository talkRepository) {
    this.ticketRepository = ticketRepository;
    this.talkRepository = talkRepository;
  }

  @Retryable(
      value = {ObjectOptimisticLockingFailureException.class},
      maxAttempts = 5,
      backoff = @Backoff(delay = 100) // 100 ms delay between retries
      )
  @Transactional
  public String reserveTicket(Long talkId, String userEmail) {
    TalkEntity talk =
        talkRepository
            .findById(talkId)
            .orElseThrow(() -> new IllegalArgumentException("Talk not found"));

    if (!talk.tryReserveSeat()) {
      return "Sold out";
    }

    talkRepository.save(talk);

    Ticket ticket =
        new Ticket(
            null,
            talkId,
            userEmail,
            new ReservationResult.Reserved(),
            LocalDateTime.now(),
            LocalDateTime.now().plusMinutes(10));

    TicketEntity ticketEntity = Mapper.ticketDomainToEntityMapper(ticket);

    ticketRepository.save(ticketEntity);

    return "Reserved! Expires at " + ticket.expiresAt();
  }

  @Transactional
  public String confirmPayment(Long ticketId) {
    TicketEntity ticket =
        ticketRepository
            .findById(ticketId)
            .orElseThrow(() -> new IllegalArgumentException("Invalid Ticket Id"));
    if (checkConfirmPaymentCondition(ticket)) {
      return confirmPayment(ticket);
    } else return "Already expired or invalid";
  }

  @Transactional
  public void expireOldReservations() {
    // Placeholder for scheduler – scans and updates expired ones
    // This will be inefficient and miss races
    List<TicketEntity> reserved =
        ticketRepository.findByTalkIdAndStatus(
            1L,
            com.sherwin.conference.bookingsystem.entity.ReservationResult.Reserved
                .RESERVED); // Hardcoded talkId
    LocalDateTime now = LocalDateTime.now();
    for (TicketEntity ticket : reserved) {
      if (ticket.getExpiresAt().isBefore(now)) {
        ticket.setStatus(
            new com.sherwin.conference.bookingsystem.entity.ReservationResult.Expired());
        ticketRepository.save(ticket);
      }
    }
  }

  private String confirmPayment(TicketEntity ticket) {
    ticket.setStatus(new com.sherwin.conference.bookingsystem.entity.ReservationResult.Paid());
    ticketRepository.save(ticket);
    return "Confirmed";
  }

  private Boolean checkConfirmPaymentCondition(TicketEntity ticket) {
    return switch (ticket.getStatus()) {
      case com.sherwin.conference.bookingsystem.entity.ReservationResult.Reserved reserved ->
          Boolean.TRUE;
      case com.sherwin.conference.bookingsystem.entity.ReservationResult.Expired expired ->
          Boolean.FALSE;
      case com.sherwin.conference.bookingsystem.entity.ReservationResult.Paid paid -> Boolean.FALSE;
      case null -> throw new IllegalArgumentException();
    };
  }
}

package com.sherwin.conference.bookingsystem.domain.feature;

import com.sherwin.conference.bookingsystem.domain.DomainService;
import com.sherwin.conference.bookingsystem.domain.ReservationResult;
import com.sherwin.conference.bookingsystem.domain.Talk;
import com.sherwin.conference.bookingsystem.domain.Ticket;
import com.sherwin.conference.bookingsystem.domain.event.ConferenceApplicationEvent;
import com.sherwin.conference.bookingsystem.domain.spi.ConfirmPaymentAction;
import com.sherwin.conference.bookingsystem.domain.spi.EventPublisher;
import com.sherwin.conference.bookingsystem.domain.spi.ExpireOldReservationsAction;
import com.sherwin.conference.bookingsystem.domain.spi.ReserveTicketAction;
import java.time.LocalDateTime;
import java.util.List;

@DomainService
public class TicketDomainService {
  private final ReserveTicketAction reserveTicketAction;

  private final ConfirmPaymentAction confirmPaymentAction;

  private final ExpireOldReservationsAction expireOldReservationsAction;

  private final EventPublisher eventPublisher;

  public TicketDomainService(
      ReserveTicketAction reserveTicketAction,
      ConfirmPaymentAction confirmPaymentAction,
      ExpireOldReservationsAction expireOldReservationsAction,
      EventPublisher eventPublisher) {
    this.reserveTicketAction = reserveTicketAction;
    this.confirmPaymentAction = confirmPaymentAction;
    this.expireOldReservationsAction = expireOldReservationsAction;
    this.eventPublisher = eventPublisher;
  }

  public String reserveTicket(Long talkId, String userEmail) {
    Talk talk =
        reserveTicketAction
            .fetchTalkById(talkId)
            .orElseThrow(() -> new IllegalArgumentException("Talk not found"));

    if (!talk.tryReserveSeat()) {
      return "Sold out";
    }

    int newReservedSeatCount = talk.reservedSeats() + 1;
    Talk talkToSave =
        new Talk(talk.id(), talk.name(), talk.totalSeats(), newReservedSeatCount, talk.version());

    Talk savedTalk = reserveTicketAction.saveTalk(talkToSave);

    Ticket ticket =
        new Ticket(
            null,
            talkId,
            userEmail,
            new ReservationResult.Reserved(),
            LocalDateTime.now(),
            LocalDateTime.now().plusMinutes(10));

    Ticket savedTicket = reserveTicketAction.saveTicket(ticket);

    eventPublisher.publish(
        new ConferenceApplicationEvent.SeatReserved(savedTicket.id(), savedTicket.reservedAt()),
        Boolean.TRUE);
    return "Reserved! Expires at " + savedTicket.expiresAt();
  }

  public String confirmPayment(Long ticketId) {
    Ticket ticket =
        confirmPaymentAction
            .findTicketById(ticketId)
            .orElseThrow(() -> new IllegalArgumentException("Invalid Ticket Id"));
    if (checkConfirmPaymentCondition(ticket)) {
      return confirmPayment(ticket);
    } else return "Already expired or invalid";
  }

  public void expireOldReservations() {
    // Placeholder for scheduler – scans and updates expired ones
    // This will be inefficient and miss races
    List<Ticket> reserved =
        expireOldReservationsAction.findByTalkIdAndStatus(
            1L, new ReservationResult.Reserved()); // Hardcoded talkId
    LocalDateTime now = LocalDateTime.now();
    for (Ticket ticket : reserved) {
      if (ticket.expiresAt().isBefore(now)) {
        Ticket ticketToSave =
            new Ticket(
                ticket.id(),
                ticket.talkId(),
                ticket.userEmail(),
                new ReservationResult.Expired(),
                ticket.reservedAt(),
                ticket.expiresAt());
        expireOldReservationsAction.saveTicket(ticketToSave);
      }
    }
  }

  private String confirmPayment(Ticket ticket) {
    Ticket ticketToSave =
        new Ticket(
            ticket.id(),
            ticket.talkId(),
            ticket.userEmail(),
            new ReservationResult.Paid(),
            ticket.reservedAt(),
            ticket.expiresAt());
    confirmPaymentAction.saveTicket(ticket);
    return "Confirmed";
  }

  private Boolean checkConfirmPaymentCondition(Ticket ticket) {
    return switch (ticket.status()) {
      case ReservationResult.Reserved reserved -> Boolean.TRUE;
      case ReservationResult.Expired expired -> Boolean.FALSE;
      case ReservationResult.Paid paid -> Boolean.FALSE;
      case null -> throw new IllegalArgumentException();
    };
  }
}

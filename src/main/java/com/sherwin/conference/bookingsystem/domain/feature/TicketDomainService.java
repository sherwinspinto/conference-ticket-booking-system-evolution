package com.sherwin.conference.bookingsystem.domain.feature;

import com.sherwin.conference.bookingsystem.domain.DomainService;
import com.sherwin.conference.bookingsystem.domain.ReservationResult;
import com.sherwin.conference.bookingsystem.domain.Talk;
import com.sherwin.conference.bookingsystem.domain.Ticket;
import com.sherwin.conference.bookingsystem.domain.event.model.ConferenceApplicationEvent;
import com.sherwin.conference.bookingsystem.domain.spi.ConfirmPaymentAction;
import com.sherwin.conference.bookingsystem.domain.spi.EventPublisher;
import com.sherwin.conference.bookingsystem.domain.spi.ReserveTicketAction;
import java.time.LocalDateTime;

@DomainService
public class TicketDomainService {
  private final ReserveTicketAction reserveTicketAction;

  private final ConfirmPaymentAction confirmPaymentAction;

  private final EventPublisher eventPublisher;

  public TicketDomainService(
      ReserveTicketAction reserveTicketAction,
      ConfirmPaymentAction confirmPaymentAction,
      EventPublisher eventPublisher) {
    this.reserveTicketAction = reserveTicketAction;
    this.confirmPaymentAction = confirmPaymentAction;
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
            LocalDateTime.now());

    Ticket savedTicket = reserveTicketAction.saveTicket(ticket);

    eventPublisher.publish(
        new ConferenceApplicationEvent.ReservedTicket(savedTicket.id(), savedTicket.reservedAt()),
        Boolean.TRUE);
    return "Reserved!";
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

  private String confirmPayment(Ticket ticket) {
    Ticket ticketToSave =
        new Ticket(
            ticket.id(),
            ticket.talkId(),
            ticket.userEmail(),
            new ReservationResult.Paid(),
            ticket.reservedAt());
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

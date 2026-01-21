package com.sherwin.conference.bookingsystem.domain.feature.ticket.api;

import static com.sherwin.conference.bookingsystem.domain.feature.model.Id.*;
import static com.sherwin.conference.bookingsystem.domain.feature.ticket.model.TicketFieldType.*;

import com.sherwin.conference.bookingsystem.domain.DomainService;
import com.sherwin.conference.bookingsystem.domain.event.api.port.SaveReserveSeatEventPort;
import com.sherwin.conference.bookingsystem.domain.event.model.ConferenceApplicationEvent;
import com.sherwin.conference.bookingsystem.domain.event.model.EventAggregate;
import com.sherwin.conference.bookingsystem.domain.event.model.EventCommand;
import com.sherwin.conference.bookingsystem.domain.event.spi.DomainEventPublisherPort;
import com.sherwin.conference.bookingsystem.domain.feature.talk.api.port.CheckIfTalkExistsPort;
import com.sherwin.conference.bookingsystem.domain.feature.talk.api.port.ReserveSeatPort;
import com.sherwin.conference.bookingsystem.domain.feature.ticket.api.port.ReserveTicketPort;
import com.sherwin.conference.bookingsystem.domain.feature.ticket.model.*;
import com.sherwin.conference.bookingsystem.domain.feature.ticket.model.*;
import com.sherwin.conference.bookingsystem.domain.feature.ticket.spi.ReserveTicketDbPort;
import java.time.Clock;
import java.time.LocalDateTime;

@DomainService
public class ReserveTicketService implements ReserveTicketPort {
  private final ReserveTicketDbPort reserveTicketDbPort;
  private final ReserveSeatPort reserveSeatPort;
  private final CheckIfTalkExistsPort checkIfTalkExistsPort;
  private final Clock clock;
  private final DomainEventPublisherPort<ConferenceApplicationEvent> eventPublisher;
  private final SaveReserveSeatEventPort saveReserveSeatEventPort;

  public ReserveTicketService(
      ReserveTicketDbPort reserveTicketDbPort,
      ReserveSeatPort reserveSeatPort,
      CheckIfTalkExistsPort checkIfTalkExistsPort,
      Clock clock,
      DomainEventPublisherPort<ConferenceApplicationEvent> eventPublisher,
      SaveReserveSeatEventPort saveReserveSeatEventPort) {
    this.reserveTicketDbPort = reserveTicketDbPort;
    this.reserveSeatPort = reserveSeatPort;
    this.checkIfTalkExistsPort = checkIfTalkExistsPort;
    this.clock = clock;
    this.eventPublisher = eventPublisher;
    this.saveReserveSeatEventPort = saveReserveSeatEventPort;
  }

  @Override
  public ReserveTicketResult reserveTicket(ReserveTicketInput reserveTicketInput) {
    ReserveTicket reserveTicket =
        new ReserveTicket(
            new TalkId(reserveTicketInput.talkId().value()),
            reserveTicketInput.userEmail(),
            ReservationStatus.RESERVED,
            new ReservedAt(LocalDateTime.now(clock)));
    boolean talkIdExists = checkIfTalkExistsPort.checkIfTalkExists(reserveTicketInput.talkId());
    if (talkIdExists) {
      return recordReservation(reserveTicket, clock);
    } else {
      return new ReserveTicketResult.Failure(
          ReserveTicketResult.ReservationFailureReason.TALK_NOT_FOUND);
    }
  }

  ReserveTicketResult recordReservation(ReserveTicket reserveTicket, Clock clock) {
    boolean seatReserved = reserveSeatPort.reserveSeat(reserveTicket.talkId());
    if (seatReserved) {
      Ticket reservedTicket = reserveTicketDbPort.reserveTicket(reserveTicket);
      ConferenceApplicationEvent.ReservedTicket event =
          new ConferenceApplicationEvent.ReservedTicket(
              reservedTicket.ticketId().value(), reservedTicket.reservedAt().value());
      EventCommand.SaveReserveTicketEvent saveReserveTicketEvent =
          new EventCommand.SaveReserveTicketEvent(
              reservedTicket.ticketId(),
              event,
              new EventAggregate.CreatedAt(LocalDateTime.now(clock)));
      EventAggregate eventAggregate =
          saveReserveSeatEventPort.saveReserveSeatEvent(saveReserveTicketEvent);
      eventPublisher.publish(event);
      return new ReserveTicketResult.SuccessTicketAndEvent(reservedTicket, eventAggregate);
    } else {
      return new ReserveTicketResult.Failure(
          ReserveTicketResult.ReservationFailureReason.TICKET_LIMIT_EXCEEDED);
    }
  }
}

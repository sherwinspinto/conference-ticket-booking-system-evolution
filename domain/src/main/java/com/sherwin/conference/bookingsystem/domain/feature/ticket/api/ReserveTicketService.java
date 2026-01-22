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
import com.sherwin.conference.bookingsystem.domain.feature.talk.api.port.GetTalkPort;
import com.sherwin.conference.bookingsystem.domain.feature.talk.api.port.ReserveSeatPort;
import com.sherwin.conference.bookingsystem.domain.feature.talk.model.Talk;
import com.sherwin.conference.bookingsystem.domain.feature.ticket.api.port.ReserveTicketPort;
import com.sherwin.conference.bookingsystem.domain.feature.ticket.model.*;
import com.sherwin.conference.bookingsystem.domain.feature.ticket.model.*;
import com.sherwin.conference.bookingsystem.domain.feature.ticket.spi.ReserveTicketDbPort;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.Optional;

@DomainService
public class ReserveTicketService implements ReserveTicketPort {
  private final ReserveTicketDbPort reserveTicketDbPort;
  private final ReserveSeatPort reserveSeatPort;
  private final GetTalkPort getTalkPort;
  private final Clock clock;
  private final DomainEventPublisherPort<ConferenceApplicationEvent> eventPublisher;
  private final SaveReserveSeatEventPort saveReserveSeatEventPort;

  public ReserveTicketService(
      ReserveTicketDbPort reserveTicketDbPort,
      ReserveSeatPort reserveSeatPort,
      GetTalkPort getTalkPort,
      Clock clock,
      DomainEventPublisherPort<ConferenceApplicationEvent> eventPublisher,
      SaveReserveSeatEventPort saveReserveSeatEventPort) {
    this.reserveTicketDbPort = reserveTicketDbPort;
    this.reserveSeatPort = reserveSeatPort;
    this.getTalkPort = getTalkPort;
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

    Optional<Talk> talk = getTalkPort.getTalkById(reserveTicketInput.talkId());

    return talk.map(talk1 -> recordReservation(reserveTicket, talk1, clock)).orElseGet(() -> new ReserveTicketResult.Failure(
       ReserveTicketResult.ReservationFailureReason.TALK_NOT_FOUND));

//    if (talk.isPresent()) {
//      return recordReservation(reserveTicket, talk.get(), clock);
//    } else {
//      return new ReserveTicketResult.Failure(
//          ReserveTicketResult.ReservationFailureReason.TALK_NOT_FOUND);
//    }
  }

  ReserveTicketResult recordReservation(ReserveTicket reserveTicket, Talk talk, Clock clock) {
    Optional<Talk> seatReservedTalk = reserveSeatPort.reserveSeat(talk);
    if (seatReservedTalk.isPresent()) {
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

package com.sherwin.conference.bookingsystem.domain.event.model;

import com.sherwin.conference.bookingsystem.domain.feature.model.Id.TicketId;

public sealed interface EventCommand {

  record SaveReserveTicketEvent(
      TicketId ticketId,
      ConferenceApplicationEvent.ReservedTicket event,
      EventAggregate.CreatedAt createdAt)
      implements EventCommand {
    public EventAggregate.Status status() {
      return EventAggregate.Status.PENDING;
    }
  }

  record ExpireReservation(
      TicketId ticketId,
      ConferenceApplicationEvent.ReservationExpired event,
      EventAggregate.CreatedAt createdAt)
      implements EventCommand {
    public EventAggregate.Status status() {
      return EventAggregate.Status.PENDING;
    }
  }
}

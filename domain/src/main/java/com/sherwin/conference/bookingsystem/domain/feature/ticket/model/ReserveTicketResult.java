package com.sherwin.conference.bookingsystem.domain.feature.ticket.model;

import com.sherwin.conference.bookingsystem.domain.event.model.EventAggregate;

public sealed interface ReserveTicketResult {
  record SuccessTicketAndEvent(Ticket ticket, EventAggregate eventAggregate)
      implements ReserveTicketResult {}

  record Failure(ReservationFailureReason reason) implements ReserveTicketResult {}

  enum ReservationFailureReason {
    TALK_NOT_FOUND,
    TICKET_LIMIT_EXCEEDED,
    JSON_SERIALIZATION_ERROR
  }
}

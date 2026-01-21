package com.sherwin.conference.bookingsystem.domain.event.model;

import java.time.LocalDateTime;

// TODO: Add more events as needed
// TODO: Separate events by bounded context if necessary (Tickets, Talks)
public sealed interface ConferenceApplicationEvent {
  EventType eventType();

  enum EventType {
    TICKET_RESERVED,
    RESERVATION_EXPIRED
  }

  record ReservedTicket(Long ticketId, LocalDateTime reservedAt)
      implements ConferenceApplicationEvent {

    @Override
    public EventType eventType() {
      return EventType.TICKET_RESERVED;
    }
  }

  record ReservationExpired(Long ticketId, LocalDateTime expiredAt)
      implements ConferenceApplicationEvent {
    @Override
    public EventType eventType() {
      return EventType.RESERVATION_EXPIRED;
    }
  }
}

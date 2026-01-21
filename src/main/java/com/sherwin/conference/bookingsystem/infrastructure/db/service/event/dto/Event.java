package com.sherwin.conference.bookingsystem.infrastructure.db.service.event.dto;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.sherwin.conference.bookingsystem.domain.event.model.ConferenceApplicationEvent;

import java.time.LocalDateTime;

@JsonTypeInfo(
  use = JsonTypeInfo.Id.NAME,
  include = JsonTypeInfo.As.PROPERTY,
  property = "type"
)
@JsonSubTypes({
  @JsonSubTypes.Type(value = Event.ReservedTicket.class, name = "ticketReserved"),
  @JsonSubTypes.Type(value = Event.ReservationExpired.class, name = "reservationExpired")
})
public sealed interface Event {
  static Event fromDomain(ConferenceApplicationEvent event) {
    return switch (event) {
      case ConferenceApplicationEvent.ReservedTicket reservedTicket -> new ReservedTicket(reservedTicket.ticketId(), reservedTicket.reservedAt());
      case ConferenceApplicationEvent.ReservationExpired expiredTicket -> new ReservationExpired(expiredTicket.ticketId(), expiredTicket.expiredAt());
    };
  }

  static ConferenceApplicationEvent toDomain(Event event) {
    return switch (event) {
      case ReservedTicket reservedTicket -> new ConferenceApplicationEvent.ReservedTicket(reservedTicket.ticketId(), reservedTicket.reservedAt());
      case ReservationExpired expiredTicket -> new ConferenceApplicationEvent.ReservationExpired(expiredTicket.ticketId(), expiredTicket.expiredAt());
    };
  }

  @JsonTypeName("ticketReserved")
  record ReservedTicket(Long ticketId, LocalDateTime reservedAt) implements Event {}

  @JsonTypeName("reservationExpired")
  record ReservationExpired(Long ticketId, LocalDateTime expiredAt) implements Event {}
}

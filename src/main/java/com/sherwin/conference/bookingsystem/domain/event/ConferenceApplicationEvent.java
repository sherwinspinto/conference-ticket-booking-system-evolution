package com.sherwin.conference.bookingsystem.domain.event;

import java.time.LocalDateTime;

public sealed interface ConferenceApplicationEvent {
  record SeatReserved(Long ticketId, LocalDateTime reservedAt) implements ConferenceApplicationEvent {}
}

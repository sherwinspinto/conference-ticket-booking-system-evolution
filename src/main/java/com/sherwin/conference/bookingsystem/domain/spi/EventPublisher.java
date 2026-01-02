package com.sherwin.conference.bookingsystem.domain.spi;

import com.sherwin.conference.bookingsystem.domain.event.ConferenceApplicationEvent;

public interface EventPublisher {
  Boolean publish(ConferenceApplicationEvent seatReserved, boolean success);
}

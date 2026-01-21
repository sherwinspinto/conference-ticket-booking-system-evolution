package com.sherwin.conference.bookingsystem.domain.spi;

import com.sherwin.conference.bookingsystem.domain.event.model.ConferenceApplicationEvent;

public interface EventPublisher {
  Boolean publish(ConferenceApplicationEvent seatReserved, boolean success);
}

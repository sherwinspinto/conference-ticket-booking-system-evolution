package com.sherwin.conference.bookingsystem.domain.feature;

import com.sherwin.conference.bookingsystem.domain.DomainService;
import com.sherwin.conference.bookingsystem.domain.event.model.ConferenceApplicationEvent.ReservationExpired;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@DomainService
public class AfterSeatExpiryService {
  private static final Logger LOGGER = LoggerFactory.getLogger(AfterSeatExpiryService.class);

  public boolean handleSeatExpiryEvent(ReservationExpired reservationExpired) {
    LOGGER.info(
        "Seat with ticketId: {} expired successfully at {}",
        reservationExpired.ticketId(),
        reservationExpired.expiredAt());
    return Boolean.TRUE;
  }
}

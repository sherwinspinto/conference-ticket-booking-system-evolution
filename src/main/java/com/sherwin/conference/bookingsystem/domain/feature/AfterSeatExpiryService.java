package com.sherwin.conference.bookingsystem.domain.feature;

import com.sherwin.conference.bookingsystem.domain.DomainService;
import com.sherwin.conference.bookingsystem.domain.event.ConferenceApplicationEvent.SeatExpired;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@DomainService
public class AfterSeatExpiryService {
  private static final Logger LOGGER = LoggerFactory.getLogger(AfterSeatExpiryService.class);

  public boolean handleSeatExpiryEvent(SeatExpired seatExpired) {
    LOGGER.info(
        "Seat with ticketId: {} expired successfully at {}",
        seatExpired.ticketId(),
        seatExpired.expiredAt());
    return Boolean.TRUE;
  }
}

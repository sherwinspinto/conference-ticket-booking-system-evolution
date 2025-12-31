package com.sherwin.conference.bookingsystem.domain.feature;

import com.sherwin.conference.bookingsystem.domain.DomainService;
import com.sherwin.conference.bookingsystem.domain.event.ConferenceApplicationEvent;
import com.sherwin.conference.bookingsystem.domain.event.ConferenceApplicationEvent.SeatReserved;

@DomainService
public class DomainEventHandler implements EventHandler {
  private final SeatExpiryService seatExpiryService;

  public DomainEventHandler(SeatExpiryService seatExpiryService) {
    this.seatExpiryService = seatExpiryService;
  }

  @Override
  public boolean handleEvent(ConferenceApplicationEvent event) {
    return switch (event) {
      case SeatReserved seatReserved -> seatExpiryService.expireSeat(seatReserved);
    };
  }
}

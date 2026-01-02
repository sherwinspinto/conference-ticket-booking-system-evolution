package com.sherwin.conference.bookingsystem.domain.feature;

import com.sherwin.conference.bookingsystem.domain.DomainService;
import com.sherwin.conference.bookingsystem.domain.event.ConferenceApplicationEvent;
import com.sherwin.conference.bookingsystem.domain.event.ConferenceApplicationEvent.SeatExpired;
import com.sherwin.conference.bookingsystem.domain.event.ConferenceApplicationEvent.SeatReserved;

@DomainService
public class DomainEventHandler implements EventHandler {
  private final SeatExpiryDomainService seatExpiryService;
  private final AfterSeatExpiryService afterSeatExpiryService;

  public DomainEventHandler(
    SeatExpiryDomainService seatExpiryDomainService, AfterSeatExpiryService afterSeatExpiryService) {
    this.seatExpiryService = seatExpiryDomainService;
    this.afterSeatExpiryService = afterSeatExpiryService;
  }

  @Override
  public boolean handleEvent(ConferenceApplicationEvent event) {
    return switch (event) {
      case SeatReserved seatReserved -> seatExpiryService.expireSeat(seatReserved);
      case SeatExpired seatExpired -> afterSeatExpiryService.handleSeatExpiryEvent(seatExpired);
    };
  }
}

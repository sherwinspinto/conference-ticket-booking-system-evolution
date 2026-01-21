package com.sherwin.conference.bookingsystem.domain.feature;

import com.sherwin.conference.bookingsystem.domain.DomainService;
import com.sherwin.conference.bookingsystem.domain.event.model.ConferenceApplicationEvent;
import com.sherwin.conference.bookingsystem.domain.event.model.ConferenceApplicationEvent.ReservationExpired;
import com.sherwin.conference.bookingsystem.domain.event.model.ConferenceApplicationEvent.ReservedTicket;

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
      case ReservedTicket reservedTicket -> seatExpiryService.expireSeat(reservedTicket);
      case ReservationExpired reservationExpired -> afterSeatExpiryService.handleSeatExpiryEvent(reservationExpired);
    };
  }
}

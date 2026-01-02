package com.sherwin.conference.bookingsystem.domain.spi;

public interface SeatExpiryService {
  public boolean expireSeat(Long ticketId);
}

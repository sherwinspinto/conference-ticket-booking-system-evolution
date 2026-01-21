package com.sherwin.conference.bookingsystem.domain.event.spi;

import com.sherwin.conference.bookingsystem.domain.event.model.EventAggregate;
import com.sherwin.conference.bookingsystem.domain.event.model.EventCommand;

public interface SaveReserveSeatEventDbPort {
  EventAggregate saveReserveSeatEvent(EventCommand.SaveReserveTicketEvent eventAggregate);
}

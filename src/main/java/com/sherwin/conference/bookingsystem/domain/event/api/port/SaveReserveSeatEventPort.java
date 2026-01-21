package com.sherwin.conference.bookingsystem.domain.event.api.port;

import com.sherwin.conference.bookingsystem.domain.event.model.EventAggregate;
import com.sherwin.conference.bookingsystem.domain.event.model.EventCommand;

public interface SaveReserveSeatEventPort {
  EventAggregate saveReserveSeatEvent(EventCommand.SaveReserveTicketEvent eventAggregate);
}

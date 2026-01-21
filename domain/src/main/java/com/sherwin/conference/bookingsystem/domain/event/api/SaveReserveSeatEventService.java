package com.sherwin.conference.bookingsystem.domain.event.api;

import com.sherwin.conference.bookingsystem.domain.DomainService;
import com.sherwin.conference.bookingsystem.domain.event.api.port.SaveReserveSeatEventPort;
import com.sherwin.conference.bookingsystem.domain.event.model.EventAggregate;
import com.sherwin.conference.bookingsystem.domain.event.model.EventCommand;
import com.sherwin.conference.bookingsystem.domain.event.spi.SaveReserveSeatEventDbPort;

@DomainService
public class SaveReserveSeatEventService implements SaveReserveSeatEventPort {
  private final SaveReserveSeatEventDbPort saveReserveSeatEventDbPort;

  public SaveReserveSeatEventService(SaveReserveSeatEventDbPort saveReserveSeatEventDbPort) {
    this.saveReserveSeatEventDbPort = saveReserveSeatEventDbPort;
  }
  @Override
  public EventAggregate saveReserveSeatEvent(EventCommand.SaveReserveTicketEvent saveReserveTicketEvent) {
    return saveReserveSeatEventDbPort.saveReserveSeatEvent(saveReserveTicketEvent);
  }
}

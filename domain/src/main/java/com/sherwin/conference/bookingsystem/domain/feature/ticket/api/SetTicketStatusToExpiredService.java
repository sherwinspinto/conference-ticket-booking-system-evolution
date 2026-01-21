package com.sherwin.conference.bookingsystem.domain.feature.ticket.api;

import com.sherwin.conference.bookingsystem.domain.DomainService;
import com.sherwin.conference.bookingsystem.domain.feature.ticket.api.port.SetTicketStatusToExpiredPort;
import com.sherwin.conference.bookingsystem.domain.feature.ticket.model.SetTicketStatusToExpired;
import com.sherwin.conference.bookingsystem.domain.feature.ticket.model.Ticket;
import com.sherwin.conference.bookingsystem.domain.feature.ticket.spi.SetTicketStatusToExpiredDbPort;

import java.util.Optional;

@DomainService
public class SetTicketStatusToExpiredService implements SetTicketStatusToExpiredPort {
  private final SetTicketStatusToExpiredDbPort setTicketStatusToExpiredDbPort;

  public SetTicketStatusToExpiredService(SetTicketStatusToExpiredDbPort setTicketStatusToExpiredDbPort) {
    this.setTicketStatusToExpiredDbPort = setTicketStatusToExpiredDbPort;
  }

  @Override
  public Optional<Ticket> setTicketStatusToExpired(SetTicketStatusToExpired ticketId) {
    return setTicketStatusToExpiredDbPort.setStatusToExpired(ticketId);
  }
}

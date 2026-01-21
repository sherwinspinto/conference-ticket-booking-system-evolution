package com.sherwin.conference.bookingsystem.domain.feature.ticket.spi;

import com.sherwin.conference.bookingsystem.domain.feature.ticket.model.SetTicketStatusToExpired;
import com.sherwin.conference.bookingsystem.domain.feature.ticket.model.Ticket;

import java.util.Optional;

public interface SetTicketStatusToExpiredDbPort {
  Optional<Ticket> setStatusToExpired(SetTicketStatusToExpired ticketId);
}

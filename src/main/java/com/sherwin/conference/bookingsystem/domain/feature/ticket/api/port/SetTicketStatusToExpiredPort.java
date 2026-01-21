package com.sherwin.conference.bookingsystem.domain.feature.ticket.api.port;

import com.sherwin.conference.bookingsystem.domain.feature.ticket.model.SetTicketStatusToExpired;
import com.sherwin.conference.bookingsystem.domain.feature.ticket.model.Ticket;

import java.util.Optional;

public interface SetTicketStatusToExpiredPort {
  Optional<Ticket> setTicketStatusToExpired(SetTicketStatusToExpired ticketId);
}

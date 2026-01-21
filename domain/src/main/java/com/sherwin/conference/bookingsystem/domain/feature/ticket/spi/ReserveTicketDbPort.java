package com.sherwin.conference.bookingsystem.domain.feature.ticket.spi;

import com.sherwin.conference.bookingsystem.domain.feature.ticket.model.ReserveTicket;
import com.sherwin.conference.bookingsystem.domain.feature.ticket.model.Ticket;

public interface ReserveTicketDbPort {
  Ticket reserveTicket(ReserveTicket reserveTicket);
}

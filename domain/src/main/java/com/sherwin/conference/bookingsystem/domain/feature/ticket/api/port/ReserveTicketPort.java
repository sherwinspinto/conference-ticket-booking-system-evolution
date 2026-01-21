package com.sherwin.conference.bookingsystem.domain.feature.ticket.api.port;

import com.sherwin.conference.bookingsystem.domain.feature.ticket.model.ReserveTicketInput;
import com.sherwin.conference.bookingsystem.domain.feature.ticket.model.ReserveTicketResult;

public interface ReserveTicketPort {
  ReserveTicketResult reserveTicket(ReserveTicketInput reserveTicketInput);
}

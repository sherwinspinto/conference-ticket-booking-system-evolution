package com.sherwin.conference.bookingsystem.domain.spi;

import com.sherwin.conference.bookingsystem.domain.Ticket;

import java.util.Optional;

public interface ConfirmPaymentAction {
  Optional<Ticket> findTicketById (Long id);

  Ticket saveTicket(Ticket ticket);
}

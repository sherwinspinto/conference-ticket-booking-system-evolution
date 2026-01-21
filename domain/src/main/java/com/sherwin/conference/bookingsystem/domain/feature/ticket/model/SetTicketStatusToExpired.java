package com.sherwin.conference.bookingsystem.domain.feature.ticket.model;


import static com.sherwin.conference.bookingsystem.domain.feature.model.Id.*;

public record SetTicketStatusToExpired(TicketId ticketId) {
  public SetTicketStatusToExpired {
    if (ticketId == null) {
      throw new IllegalArgumentException("ticketId cannot be null");
    }
  }
}

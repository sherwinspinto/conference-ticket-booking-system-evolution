package com.sherwin.conference.bookingsystem.infrastructure.db.ticket;

import com.sherwin.conference.bookingsystem.domain.feature.ticket.model.ReserveTicket;
import com.sherwin.conference.bookingsystem.domain.feature.ticket.model.Ticket;
import com.sherwin.conference.bookingsystem.domain.feature.ticket.spi.ReserveTicketDbPort;
import com.sherwin.conference.bookingsystem.infrastructure.db.entity.ticket.TicketEntity2;
import com.sherwin.conference.bookingsystem.infrastructure.db.respository.TicketRepository2;
import org.springframework.stereotype.Service;

@Service
public class ReserveTicketJPA implements ReserveTicketDbPort {
  private final TicketRepository2 ticketRepository;

  public ReserveTicketJPA(TicketRepository2 ticketRepository) {
    this.ticketRepository = ticketRepository;
  }

  @Override
  public Ticket reserveTicket(ReserveTicket reserveTicket) {
    TicketEntity2 ticketEntity = TicketEntity2.fromDomain(reserveTicket);
    return TicketEntity2.toDomain(ticketRepository.save(ticketEntity));
  }
}

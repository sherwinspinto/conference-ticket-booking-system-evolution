package com.sherwin.conference.bookingsystem.infrastructure.db.service.ticket;

import com.sherwin.conference.bookingsystem.domain.feature.ticket.model.ReserveTicket;
import com.sherwin.conference.bookingsystem.domain.feature.ticket.model.Ticket;
import com.sherwin.conference.bookingsystem.domain.feature.ticket.spi.ReserveTicketDbPort;
import com.sherwin.conference.bookingsystem.infrastructure.db.entity.ticket.TicketEntity;
import com.sherwin.conference.bookingsystem.infrastructure.db.respository.TicketRepository;
import org.springframework.stereotype.Service;

@Service
public class ReserveTicketJPA implements ReserveTicketDbPort {
  private final TicketRepository ticketRepository;

  public ReserveTicketJPA(TicketRepository ticketRepository) {
    this.ticketRepository = ticketRepository;
  }

  @Override
  public Ticket reserveTicket(ReserveTicket reserveTicket) {
    TicketEntity ticketEntity = TicketEntity.fromDomain(reserveTicket);
    return TicketEntity.toDomain(ticketRepository.save(ticketEntity));
  }
}

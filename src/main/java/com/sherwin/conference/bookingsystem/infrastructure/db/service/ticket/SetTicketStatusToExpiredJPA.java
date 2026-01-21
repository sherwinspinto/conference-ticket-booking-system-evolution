package com.sherwin.conference.bookingsystem.infrastructure.db.service.ticket;

import com.sherwin.conference.bookingsystem.domain.feature.ticket.model.SetTicketStatusToExpired;
import com.sherwin.conference.bookingsystem.domain.feature.ticket.model.Ticket;
import com.sherwin.conference.bookingsystem.domain.feature.ticket.spi.SetTicketStatusToExpiredDbPort;
import com.sherwin.conference.bookingsystem.infrastructure.db.entity.ticket.TicketEntity;
import com.sherwin.conference.bookingsystem.infrastructure.db.respository.TicketRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class SetTicketStatusToExpiredJPA implements SetTicketStatusToExpiredDbPort {
  private final TicketRepository ticketRepository;

  public SetTicketStatusToExpiredJPA(TicketRepository ticketRepository) {
    this.ticketRepository = ticketRepository;
  }

  @Override
  @Transactional
  public Optional<Ticket> setStatusToExpired(SetTicketStatusToExpired ticketId) {
    return ticketRepository
        .updateToExpiredAndFetch(ticketId.ticketId().value())
        .map(TicketEntity::toDomain);
  }
}

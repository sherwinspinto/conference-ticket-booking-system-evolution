package com.sherwin.conference.bookingsystem.infrastructure.repository;

import com.sherwin.conference.bookingsystem.domain.Ticket;
import com.sherwin.conference.bookingsystem.domain.mapper.Mapper;
import com.sherwin.conference.bookingsystem.domain.spi.ConfirmPaymentAction;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class ConfirmPaymentRepository implements ConfirmPaymentAction {
  private final TicketRepository ticketRepository;

  public ConfirmPaymentRepository(TicketRepository ticketRepository) {
    this.ticketRepository = ticketRepository;
  }

  @Override
  public Optional<Ticket> findTicketById(Long id) {
    return ticketRepository
        .findById(id)
        .map(Mapper::ticketEntityToTicketDomainMapper)
        .or(Optional::empty);
  }

  @Override
  public Ticket saveTicket(Ticket ticket) {
    return Mapper.ticketEntityToTicketDomainMapper(
        ticketRepository.save(Mapper.ticketDomainToEntityMapper(ticket)));
  }
}

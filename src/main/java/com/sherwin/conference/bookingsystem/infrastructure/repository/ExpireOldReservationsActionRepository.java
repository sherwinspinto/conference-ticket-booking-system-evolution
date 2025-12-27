package com.sherwin.conference.bookingsystem.infrastructure.repository;

import com.sherwin.conference.bookingsystem.domain.ReservationResult;
import com.sherwin.conference.bookingsystem.domain.Ticket;
import com.sherwin.conference.bookingsystem.domain.mapper.Mapper;
import com.sherwin.conference.bookingsystem.domain.spi.ExpireOldReservationsAction;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class ExpireOldReservationsActionRepository implements ExpireOldReservationsAction {
  private final TicketRepository ticketRepository;

  public ExpireOldReservationsActionRepository(TicketRepository ticketRepository) {
    this.ticketRepository = ticketRepository;
  }

  @Override
  public List<Ticket> findByTalkIdAndStatus(Long talkId, ReservationResult reservationResult) {
    return Optional.ofNullable(
            ticketRepository.findByTalkIdAndStatus(talkId, reservationResult.toString()))
        .map(
            ticketEntities ->
                ticketEntities.stream().map(Mapper::ticketEntityToTicketDomainMapper).toList())
        .orElse(Collections.emptyList());
  }

  @Override
  public Ticket saveTicket(Ticket ticket) {
    return Mapper.ticketEntityToTicketDomainMapper(
        ticketRepository.save(Mapper.ticketDomainToEntityMapper(ticket)));
  }
}

package com.sherwin.conference.bookingsystem.infrastructure.repository;

import com.sherwin.conference.bookingsystem.domain.Talk;
import com.sherwin.conference.bookingsystem.domain.Ticket;
import com.sherwin.conference.bookingsystem.domain.mapper.Mapper;
import com.sherwin.conference.bookingsystem.domain.spi.ReserveTicketAction;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ReserveTicketRepository implements ReserveTicketAction {

  private final TalkRepository talkRepository;
  private final TicketRepository ticketRepository;

  public ReserveTicketRepository(TalkRepository talkRepository, TicketRepository ticketRepository) {
    this.talkRepository = talkRepository;
    this.ticketRepository = ticketRepository;
  }

  @Override
  public Optional<Talk> fetchTalkById(Long talkId) {
    return talkRepository
        .findById(talkId)
        .map(Mapper::talkEntityToDomainMapper)
        .or(Optional::empty);
  }

  @Override
  public Talk saveTalk(Talk talk) {
    return Mapper.talkEntityToDomainMapper(
        talkRepository.save(Mapper.talkDomainToEntityMapper(talk)));
  }

  @Override
  public Ticket saveTicket(Ticket ticket) {
    return Mapper.ticketEntityToTicketDomainMapper(
        ticketRepository.save(Mapper.ticketDomainToEntityMapper(ticket)));
  }
}

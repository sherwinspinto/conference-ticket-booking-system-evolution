package com.sherwin.conference.bookingsystem.domain.spi;

import com.sherwin.conference.bookingsystem.domain.Talk;
import com.sherwin.conference.bookingsystem.domain.Ticket;

import java.util.Optional;

public interface ReserveTicketAction {
  Optional<Talk> fetchTalkById(Long talkId);

  Talk saveTalk(Talk talk);

  Ticket saveTicket(Ticket ticket);
}

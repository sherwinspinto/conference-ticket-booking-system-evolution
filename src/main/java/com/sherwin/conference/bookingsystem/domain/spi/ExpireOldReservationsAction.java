package com.sherwin.conference.bookingsystem.domain.spi;

import com.sherwin.conference.bookingsystem.domain.ReservationResult;
import com.sherwin.conference.bookingsystem.domain.Ticket;

import java.util.List;

public interface ExpireOldReservationsAction {
  List<Ticket> findByTalkIdAndStatus(Long talkId, ReservationResult reservationResult);
  Ticket saveTicket(Ticket ticket);
  int updateTicketStatusToExpired (Long ticketId);
}

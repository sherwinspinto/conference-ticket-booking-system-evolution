package com.sherwin.conference.bookingsystem.service;

import com.sherwin.conference.bookingsystem.domain.Ticket;
import com.sherwin.conference.bookingsystem.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class TicketService {
  @Autowired
  private TicketRepository ticketRepository;

  @Transactional
  public String reserveTicket(Long talkId, String userEmail) {
    // "Bad" logic: No atomicity, just count and hope
    List<Ticket> reserved = ticketRepository.findByTalkIdAndStatus(talkId, "RESERVED");
    if (reserved.size() >= 100) { // Magic number smell!
      return "Sold out";
    }

    Ticket ticket = new Ticket();
    ticket.setTalkId(talkId);
    ticket.setUserEmail(userEmail);
    ticket.setStatus("RESERVED");
    ticket.setReservedAt(LocalDateTime.now());
    ticket.setExpiresAt(LocalDateTime.now().plusMinutes(10));
    ticketRepository.save(ticket);

    return "Reserved! Expires at " + ticket.getExpiresAt();
  }

  @Transactional
  public String confirmPayment(Long ticketId) {
    Ticket ticket = ticketRepository.findById(ticketId).orElse(null);
    if (ticket != null && "RESERVED".equals(ticket.getStatus()) && ticket.getExpiresAt().isAfter(LocalDateTime.now())) {
      ticket.setStatus("PAID");
      ticketRepository.save(ticket);
      return "Confirmed!";
    }
    return "Invalid or already expired";
  }

  @Transactional
  public void expireOldReservations() {
    // Placeholder for scheduler – scans and updates expired ones
    // This will be inefficient and miss races
    List<Ticket> reserved = ticketRepository.findByTalkIdAndStatus(1L, "RESERVED"); // Hardcoded talkId
    LocalDateTime now = LocalDateTime.now();
    for (Ticket ticket : reserved) {
      if (ticket.getExpiresAt().isBefore(now)) {
        ticket.setStatus("EXPIRED");
        ticketRepository.save(ticket);
      }
    }
  }
}
